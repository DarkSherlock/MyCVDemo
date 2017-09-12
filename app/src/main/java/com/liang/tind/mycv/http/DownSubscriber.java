package com.liang.tind.mycv.http;

import android.content.Context;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Sherlock on 2017/9/2.
 */

public class DownSubscriber<ResponseBody> implements Observer<ResponseBody> {
    private CallBack callBack;
    private Context mContext;

    public DownSubscriber(CallBack callBack, Context context) {
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
            callBack.onSubscribe();
        }
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        DownLoadManager.getInstance(callBack).writeResponseBodyToDisk(mContext, (okhttp3.ResponseBody) responseBody);
//        com.orhanobut.logger.Logger.e(responseBody.toString());
    }
}
