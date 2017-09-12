package com.liang.tind.mycv.activity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liang.tind.mycv.R;
import com.liang.tind.mycv.utils.UIutils;
import com.liang.tind.mycv.widget.ObservableScrollView;

/**
 * Created by Sherlock on 2017/9/7.
 */

public class TestActivity extends BaseActivity {
    private android.widget.TextView tv;
    private android.widget.TextView tv1;
    private android.widget.LinearLayout llcontainer;
    private android.widget.FrameLayout flcontainer;
    private ObservableScrollView mScrollView;
    private int mHeight;
    private int mtop;
    private View mView;
    @Override
    protected void initView() {
        mScrollView = (ObservableScrollView) findViewById(R.id.scrollView);
        this.flcontainer = (FrameLayout) findViewById(R.id.fl_container);
        this.llcontainer = (LinearLayout) findViewById(R.id.ll_container);
        this.tv = (TextView) findViewById(R.id.tv);
        this.tv1 = (TextView) findViewById(R.id.tv1);
    }

    @Override
    protected void initData() {
        final int statusBarHeight = UIutils.getStatusBarHeight(this);
        mScrollView.setOnScrollChangedListener(new ObservableScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int top, int oldTop) {
                if (mtop == 0){
                    mtop =tv.getTop();
                    mHeight = tv.getHeight();
                    mView = new View(TestActivity.this);
                    mView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,mHeight));
                    mView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                if (oldTop > mtop  && tv.getParent() == llcontainer) {
                    llcontainer.removeView(tv);
                    flcontainer.addView(tv);
                    llcontainer.addView(mView,2);
                    Log.e(TAG, "come in if");
                }else if (oldTop < mtop && tv.getParent() == flcontainer){
                    flcontainer.removeView(tv);
                    llcontainer.removeView(mView);
                    llcontainer.addView(tv,2);
                    Log.e(TAG,"come in else");
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {

        return R.layout.layout_test;
    }
}
