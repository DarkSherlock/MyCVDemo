package com.liang.tind.mycv.presenter;

import com.kingja.loadsir.core.LoadService;
import com.liang.tind.mycv.view.BaseView;

/**
 * Created by Sherlock on 2017/9/3.
 */

public abstract class BaseLoadDataPresenter extends BasePresenter {
    public BaseLoadDataPresenter(BaseView baseView) {
        super(baseView);
    }

    /**
     * 请求数据
     */
    public abstract void loadData(LoadService loadService);

    /**
     * 刷新 请求数据
     */
    public abstract void refreshData();
}
