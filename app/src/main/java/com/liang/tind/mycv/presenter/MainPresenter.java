package com.liang.tind.mycv.presenter;

import com.liang.tind.mycv.Constant;
import com.liang.tind.mycv.SherlockApplicationLike;
import com.liang.tind.mycv.http.RetrofitClient;
import com.liang.tind.mycv.http.download.FileCallBack;
import com.liang.tind.mycv.model.JobInfoBean;
import com.liang.tind.mycv.view.MainView;
import com.orhanobut.logger.Logger;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by Sherlock on 2017/8/31.
 */

public class MainPresenter extends BaseLoadDataPresenter {
    private MainView mMainView;

    @Inject
    public MainPresenter(MainView mainView) {
        super(mainView);
        mMainView = mainView;
    }

    @Override
    public void loadData() {
        RetrofitClient.getInstance(SherlockApplicationLike.getApplicationInstance())
                .get(Constant.URL.JSON_JOB_INFO, new Observer<JobInfoBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull JobInfoBean jobInfoBean) {
                        if (mMainView != null) {
                            mMainView.showJobInfo(jobInfoBean);
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Logger.e(e.getMessage(), "onError");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void refreshData() {

    }


    public void downloadCV() {
        File file = new File(Constant.FILE.DOWNLOAD_DIR, Constant.FILE.CV_FILE_NAME);

        if (file.exists()) {
            mMainView.showOpenWordFileDialog(file);
        } else {
            RetrofitClient.getInstance(SherlockApplicationLike.getApplicationInstance())
                    .download(Constant.URL.BASE_URL + "img/image_head.jpg",
                            new FileCallBack<ResponseBody>(Constant.FILE.DOWNLOAD_DIR, Constant.FILE.CV_FILE_NAME) {
                                @Override
                                public void onSuccess(ResponseBody responseBody) {
                                    Logger.e("onSuccess");
                                }

                                @Override
                                public void progress(long progress, long total) {
                                    Logger.e("progress == %s  , total == %d", progress, total);
                                }

                                @Override
                                public void onStart() {
                                    Logger.e("onStart");
                                }

                                @Override
                                public void onCompleted() {
                                    if (mMainView!=null){
                                        mMainView.showCVDownloadCompleted();
                                    }

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Logger.e(e, "download img on error");
                                }
                            });
        }
    }
}
