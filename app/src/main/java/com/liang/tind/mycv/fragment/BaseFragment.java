package com.liang.tind.mycv.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Printer;

/**
 * Created by Sherlock on 2017/8/31.
 */

public abstract class BaseFragment extends Fragment{
    protected String TAG ;
    protected Printer mLogger;
    protected View mRootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        mLogger = Logger.t(TAG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(),container,false);
        initView(mRootView);
        initData();
        return getRootView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        SherlockApplicationLike.mRefWatcher.watch(this);
    }

    protected abstract void initView(View view);
    protected abstract void initData();
    protected abstract @LayoutRes int getLayoutId();
    protected View getRootView(){

        return mRootView;
    }
}
