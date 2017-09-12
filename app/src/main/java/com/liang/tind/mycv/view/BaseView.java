package com.liang.tind.mycv.view;

/**
 * Created by Sherlock on 2017/8/31.
 * 所有View 都需继承BaseView
 */

public interface BaseView {
    /**
     * show 进度 dialog
     */
    void showProcess();

    /**
     * dismiss 进度 dialog
     */
    void dismissProcess();

    /**
     * show Toast 信息
     * @param s 文字信息
     */
    void showToast(String s);

}
