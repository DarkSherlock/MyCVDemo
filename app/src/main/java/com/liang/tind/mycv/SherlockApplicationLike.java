package com.liang.tind.mycv;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.kingja.loadsir.core.LoadSir;
import com.liang.tind.mycv.view.callback.EmptyCallback;
import com.liang.tind.mycv.view.callback.ErrorCallback;
import com.liang.tind.mycv.view.callback.LoadingCallback;
import com.liang.tind.mycv.view.callback.TimeoutCallback;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.loader.app.DefaultApplicationLike;

//import skin.support.SkinCompatManager;
//import skin.support.app.SkinCardViewInflater;
//import skin.support.constraint.app.SkinConstraintViewInflater;
//import skin.support.design.app.SkinMaterialViewInflater;

/**
 * Created by Sherlock on 2017/8/16.
 */

public class SherlockApplicationLike extends DefaultApplicationLike {
    public static final String TAG = "SherlockApplicationLike";

    private static SherlockTinkerApplication application ;
    public static RefWatcher mRefWatcher;

    public SherlockApplicationLike(Application application, int tinkerFlags,
                                   boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
                                   long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        application = (SherlockTinkerApplication) getApplication();
        Bugly.init(application, "178d118966", false);
        initLeakCanary();
        initLogger();
        initLoadSir();
    }

    private void initLoadSir() {
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .setDefaultCallback(LoadingCallback.class)
                .commit();

    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy() // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("Tind")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    private void initLeakCanary() {

//        if (LeakCanary.isInAnalyzerProcess(application)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            mRefWatcher = RefWatcher.DISABLED;
//            return;
//        }
//        mRefWatcher=  LeakCanary.install(application);
    }


    public static SherlockTinkerApplication getApplicationInstance() {
        return application;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplicationInstance().registerActivityLifecycleCallbacks(callbacks);
    }
}
