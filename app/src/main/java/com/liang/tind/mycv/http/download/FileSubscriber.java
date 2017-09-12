package com.liang.tind.mycv.http.download;

import android.content.Context;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by miya95 on 2016/12/5.
 */
public class FileSubscriber<T> implements Observer<T> {
    private FileCallBack callBack;
    private Context mContext;

    public FileSubscriber(FileCallBack<T> callBack, Context context) {
        this.callBack = callBack;
        mContext = context;
    }


    @Override
    public void onError(Throwable e) {
        if (callBack != null) {
            callBack.onError(e);
        }
    }

    @Override
    public void onComplete() {
        if (callBack != null) {
            callBack.onCompleted();
        }
    }


    @Override
    public void onSubscribe(@NonNull Disposable d) {
        if (callBack != null) {
            callBack.onStart();
        }
    }

    @Override
    public void onNext(T t) {
        if (callBack != null)
            callBack.onSuccess(t);
    }

}
