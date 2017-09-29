package com.liang.tind.mycv.presenter;

import android.util.Log;

import com.kingja.loadsir.core.LoadService;
import com.liang.tind.mycv.Constant;
import com.liang.tind.mycv.SherlockApplicationLike;
import com.liang.tind.mycv.http.RetrofitClient;
import com.liang.tind.mycv.model.CvInfoBean;
import com.liang.tind.mycv.view.CvFragmentView;
import com.liang.tind.mycv.view.callback.EmptyCallback;
import com.liang.tind.mycv.view.callback.ErrorCallback;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Sherlock on 2017/9/6.
 */

public class CvFragmentPresenter extends BaseLoadDataPresenter {
    CvFragmentView mView;

    @Inject
    public CvFragmentPresenter(CvFragmentView view) {
        super(view);
        mView = view;
    }

    @Override
    public void loadData(LoadService loadService) {
        Log.e(TAG, "loadData: " );
        RetrofitClient.getInstance(SherlockApplicationLike.getApplicationInstance())
                .getCvInfo(Constant.URL.JSON_CV_INFO, new Observer<CvInfoBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CvInfoBean cvInfoBean) {
                        if (cvInfoBean == null) {
                            loadService.showCallback(EmptyCallback.class);
                        } else {
                            if (mView != null) {
                                mView.showCvDetailInfo(cvInfoBean);
                                loadService.showSuccess();
                            }
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Logger.e("onError", e.getMessage());
                        loadService.showCallback(ErrorCallback.class);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void refreshData() {

    }
}
