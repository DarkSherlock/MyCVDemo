package com.liang.tind.mycv.presenter;

import com.liang.tind.mycv.Constant;
import com.liang.tind.mycv.SherlockApplicationLike;
import com.liang.tind.mycv.http.RetrofitClient;
import com.liang.tind.mycv.model.CvInfoBean;
import com.liang.tind.mycv.view.CvFragmentView;
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
    public void loadData() {
        RetrofitClient.getInstance(SherlockApplicationLike.getApplicationInstance())
                .getCvInfo(Constant.URL.JSON_NEW_CV_INFO, new Observer<CvInfoBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CvInfoBean cvInfoBean) {
                       if (mView != null){
                           mView.showCvDetailInfo(cvInfoBean);
                       }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Logger.e("onError", e.getMessage());
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
