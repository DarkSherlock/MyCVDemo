package com.liang.tind.mycv.module;

import com.liang.tind.mycv.view.MainView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sherlock on 2017/8/31.
 */

@Module
public class MainModule {
    private MainView mMainView ;

    public MainModule(MainView mainView) {
        mMainView = mainView;
    }

    @Provides
    MainView getMainView(){
        return  mMainView;
    }
}
