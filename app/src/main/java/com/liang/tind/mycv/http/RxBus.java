package com.liang.tind.mycv.http;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


public class RxBus {

    private static volatile RxBus mInstance;
    private HashMap<String, CompositeDisposable> mSubscriptionMap;
    private Subject<Object> mSubject;

    /**
     *  PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
     *  Subject同时充当了Observer和Observable的角色，Subject是非线程安全的，要避免该问题，
     *  需要将 Subject转换为一个 SerializedSubject ，上述RxBus类中把线程非安全的PublishSubject包装成线程安全的Subject。
     */
    private RxBus() {
        mSubject = PublishSubject.create().toSerialized();
    }

    /**
     * 单例 双重锁
     * @return
     */
    public static RxBus getInstance() {
        if (mInstance == null) {
            synchronized (RxBus.class) {
                if (mInstance == null) {
                    mInstance = new RxBus();
                }
            }
        }
        return mInstance;
    }

    /**
     * 发送一个新的事件
     * @param o
     */
    public void post(Object o) {
        mSubject.onNext(o);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     * @param type
     * @param <T>
     * @return
     */
    public <T> Observable<T> tObservable(final Class<T> type) {
        //ofType操作符只发射指定类型的数据，其内部就是filter+cast
        return mSubject.ofType(type);
    }

    public <T> Disposable doSubscribe(Class<T> type, Consumer<T> next, Consumer<Throwable> error) {
        return tObservable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next, error);
    }

    public void addSubscription(Object o, Disposable subscription) {

        if (mSubscriptionMap == null) {
            mSubscriptionMap = new HashMap<>();
        }
        String key = o.getClass().getName();
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).add(subscription);
        } else {
            CompositeDisposable compositeSubscription = new CompositeDisposable();
            compositeSubscription.add(subscription);
            mSubscriptionMap.put(key, compositeSubscription);
          //  Log.e("air", "addSubscription:订阅成功 " );
        }
    }

    public void unSubscribe(Object o) {
        if (mSubscriptionMap == null) {
            return;
        }
        String key = o.getClass().getName();
        if (!mSubscriptionMap.containsKey(key)) {
            return;
        }
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).dispose();
        }
        mSubscriptionMap.remove(key);
        //Log.e("air", "unSubscribe: 取消订阅" );
    }

}
