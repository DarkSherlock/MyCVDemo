package com.liang.tind.mycv.view;

import com.liang.tind.mycv.model.JobInfoBean;

import java.io.File;

/**
 * Created by Sherlock on 2017/8/31.
 * MainActivity的 View 层
 */

public interface MainView extends BaseView  {

    /**
     * 展示 简历下载完毕 提示信息
     *
     */
    void showCVDownloadCompleted ();

    /**
     * 打开word 文件
     * @param file 文件
     */
    void openWordFile(File file);

    void showOpenWordFileDialog(File file);

    void showJobInfo(JobInfoBean bean);
}
