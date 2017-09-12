package com.liang.tind.mycv.module;

import com.liang.tind.mycv.view.CvFragmentView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sherlock on 2017/9/6.
 *
 */
@Module
public class CvModule {
    private CvFragmentView mView ;

    public CvModule(CvFragmentView view) {
        mView = view;
    }

    @Provides
    public CvFragmentView getView(){
        return mView;
    }
}
