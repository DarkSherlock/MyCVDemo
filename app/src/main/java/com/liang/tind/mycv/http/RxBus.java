package com.liang.tind.mycv.http;

import com.orhanobut.logger.Logger;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.HashMap;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;


public class RxBus {

    private static volatile RxBus mInstance;
    private HashMap<String, Subscription> mSubscriptionMap;
    private FlowableProcessor<Object> mBus;

    /**
     *  PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
     *  Subject同时充当了Observer和Observable的角色，Subject是非线程安全的，要避免该问题，
     *  需要将 Subject转换为一个 SerializedSubject ，上述RxBus类中把线程非安全的PublishSubject包装成线程安全的Subject。
     */
    private RxBus() {
        mBus = PublishProcessor.create().toSerialized();
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
        mBus.onNext(o);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     * @param type
     * @param <T>
     * @return
     */
    public <T> Flowable<T> tObservable(final Class<T> type) {
        //ofType操作符只发射指定类型的数据，其内部就是filter+cast
        return mBus.ofType(type);
    }

    public <T> void doSubscribe(Class<T> type,Subscriber<T> subscriber) {
        tObservable(type)
                .subscribeOn(Schedulers.io())
                .onBackpressureLatest()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void addSubscription(Object o, Subscription subscription) {

        if (mSubscriptionMap == null) {
            mSubscriptionMap = new HashMap<>();
        }
        String key = o.getClass().getName();
        if (mSubscriptionMap.get(key)==null) mSubscriptionMap.put(key, subscription);
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
            mSubscriptionMap.get(key).cancel();
            Logger.e("unSubscribe  cancel");
        }
        mSubscriptionMap.remove(key);
    }

}
