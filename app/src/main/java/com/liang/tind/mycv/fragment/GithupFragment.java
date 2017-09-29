package com.liang.tind.mycv.fragment;

import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.liang.tind.mycv.Constant;
import com.liang.tind.mycv.MainActivity;
import com.liang.tind.mycv.R;
import com.liang.tind.mycv.utils.ColorUtil;
import com.orhanobut.logger.Logger;

/**
 * Created by Sherlock on 2017/9/1.
 */

public class GithupFragment extends BaseFragment {

    private AgentWeb mAgentWeb;
    ChromeClientCallbackManager.ReceivedTitleCallback mCallback = (view, title) -> Logger.e("Title is %s",title);
    @Override
    protected void initView(View view) {

        //传入Activity
        mAgentWeb = AgentWeb.with(getActivity())
                //传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,
                // 第一个参数和第二个参数应该对应。
                .setAgentWebParent((FrameLayout) view, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()// 使用默认进度条
                .setIndicatorColor(ColorUtil.getColor(getContext(),R.color.colorAccent)) // 使用默认进度条颜色
                .setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()
                .ready()
                .go(Constant.URL.GITHUP_URL);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onStart() {
        super.onStart();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (!mAgentWeb.back()){
                        MainActivity activity = (MainActivity) getActivity();
                        activity.switchNavItem(R.id.nav_cv);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_webview;
    }

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();

    }


}
