package com.liang.tind.mycv;

import android.app.Application;

/**
 * Created by Sherlock on 2017/8/31.
 */

public class SherlockApplication extends Application {
    public static SherlockApplication mApplication ;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static SherlockApplication getApplication(){
        return mApplication;
    }
}
