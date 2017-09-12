package com.liang.tind.mycv.component;

import com.liang.tind.mycv.MainActivity;
import com.liang.tind.mycv.module.MainModule;

/**
 * Created by Sherlock on 2017/8/31.
 */
@dagger.Component(modules = {MainModule.class})
public interface Component {

    void inject(MainActivity mainActivity);


}
