package com.liang.tind.mycv.presenter;


import com.liang.tind.mycv.view.BaseView;
import com.orhanobut.logger.Logger;

/**
 * Created by Sherlock on 2017/8/31.
 * 所有Presenter 都需继承 BasePresenter
 */

public abstract class BasePresenter {
    protected String TAG;

    protected BaseView mBaseView;
    BasePresenter(BaseView baseView ) {
        this.mBaseView = baseView;
        TAG = this.getClass().getSimpleName();
        Logger.t(TAG);
    }

    public void onDetachView() {
        mBaseView = null;
    }
}
