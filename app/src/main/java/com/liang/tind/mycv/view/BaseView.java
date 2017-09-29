package com.liang.tind.mycv.view;

import com.liang.tind.mycv.MainActivity;

/**
 * Created by Sherlock on 2017/8/31.
 * 所有View 都需继承BaseView
 */

public interface BaseView {
    /**
     * show 进度 dialog
     * {@link MainActivity#showProcess(long, long)}
     */
    void showProcess(long process,long max);

    /**
     * dismiss 进度 dialog
     */
    void dismissProcess();

    /**
     * {@link MainActivity#error()}
     */
    void error();

    /**
     * {@link MainActivity#showProcessDialog()}
     */
    void showProcessDialog();

    /**
     * show Toast 信息
     * @param s 文字信息
     */
    void showToast(String s);

}
