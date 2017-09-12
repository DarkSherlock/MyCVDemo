package com.liang.tind.mycv.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Printer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by Sherlock on 2017/8/31.
 */

public abstract class BaseActivity extends RxAppCompatActivity {
    protected String TAG ;
    protected Printer mLogger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        TAG = this.getClass().getSimpleName();
        mLogger = Logger.t(TAG);

        initView();
        initData();
    }



    protected abstract void initView();
    protected abstract void initData();
    protected abstract @LayoutRes
    int getLayoutId();
}
