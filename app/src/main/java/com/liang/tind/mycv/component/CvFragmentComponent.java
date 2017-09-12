package com.liang.tind.mycv.component;

import com.liang.tind.mycv.fragment.CvFragment;
import com.liang.tind.mycv.module.CvModule;

/**
 * Created by Sherlock on 2017/9/7.
 */
@dagger.Component(modules = {CvModule.class})
public interface CvFragmentComponent {
    void inject(CvFragment cvFragment);
}
