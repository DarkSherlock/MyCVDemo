package com.liang.tind.mycv.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;

import com.jaeger.library.StatusBarUtil;
import com.liang.tind.mycv.MainActivity;
import com.liang.tind.mycv.R;
import com.liang.tind.mycv.widget.FlipboardIconView;

/**
 * Created by Sherlock on 2017/11/9.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void initView() {
        FlipboardIconView flipboardIconView = (FlipboardIconView) findViewById(R.id.flipborad_icon_view);
        flipboardIconView.addAnimationListener(new FlipboardIconView.AnimationListener() {
            @Override
            public void onAnimationEnd() {
                flipboardIconView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        start(SplashActivity.this);
                    }
                }, 200);

            }

            @Override
            public void onAnimationStart() {

            }
        });
    }

    public void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        finish();
    }

    @Override
    protected void initData() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.colorPrimaryDark));
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_splash;
    }
}
