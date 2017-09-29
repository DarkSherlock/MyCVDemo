package com.liang.tind.mycv.presenter;

import com.kingja.loadsir.core.LoadService;
import com.liang.tind.mycv.Constant;
import com.liang.tind.mycv.SherlockApplicationLike;
import com.liang.tind.mycv.http.RetrofitClient;
import com.liang.tind.mycv.http.RxBus;
import com.liang.tind.mycv.http.download.FileCallBack;
import com.liang.tind.mycv.model.JobInfoBean;
import com.liang.tind.mycv.view.MainView;
import com.orhanobut.logger.Logger;

import org.reactivestreams.Subscriber;

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
    private long max;
    @Inject
    public MainPresenter(MainView mainView) {
        super(mainView);
        mMainView = mainView;
    }

    @Override
    public void loadData(LoadService loadService) {
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
            if (mMainView!=null){
                mMainView.showProcessDialog();
            }
        }
    }

    public void startDownloadCV(Subscriber<MainView> s) {
        RetrofitClient.getInstance(SherlockApplicationLike.getApplicationInstance())
                .download(Constant.URL.CV_DOWNLOAD_URL,
                        new FileCallBack<ResponseBody>(Constant.FILE.DOWNLOAD_DIR, Constant.FILE.CV_FILE_NAME) {
                            @Override
                            public void onSuccess(ResponseBody responseBody) {
                                Logger.e("onSuccess");
                                RxBus.getInstance().doSubscribe(MainView.class, s);
                            }

                            @Override
                            public void progress(long progress, long total) {
                                Logger.e("progress == %s  , total == %d", progress, total);
                                if (mMainView!=null){
                                    mMainView.showProcess(progress,total);
                                    if (progress >= total) unsubscribe();
                                }
                            }

                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                if (mMainView!=null){
                                    RxBus.getInstance().post(mMainView);
                                }
                            }
                        });
    }
}
