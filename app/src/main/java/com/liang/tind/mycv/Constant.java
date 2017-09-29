package com.liang.tind.mycv;

import android.os.Environment;

/**
 * Created by Sherlock on 2017/9/1.
 * 存放全局变量类
 */

public class Constant {
    public static class URL {
        /**
         * 我的githup 主页
         */
        public static final String GITHUP_URL = "https://github.com/DarkSherlock";
        /**
         * 我的CSDN 主页
         */
        public static final String CSDN_URL = "http://blog.csdn.net/tinderliang";
        /**
         * 我的简书 主页
         */
        public static final String JIAN_SHU_URL = "http://www.jianshu.com/u/5be39d429e58";

        /**
         * BaseURL
         */
        public static final String BASE_URL = "http://o6v28pmb0.bkt.clouddn.com/";
        /**
         * 头像图片URL
         */
        public static final String IMG_HEAD_URL = "http://o6v28pmb0.bkt.clouddn.com/img/image_head.jpg";
        /**
         * 工作信息 json
         */
        public static final String JSON_JOB_INFO = "json_job_info.json";

        /**
         * 简历信息 json
         */
        public static final String JSON_CV_INFO = "json_cv_info.json";

        public static final String JSON_NEW_CV_INFO = "json_cvinfo_1.0.5.json";

        public static final String CV_DOWNLOAD_URL = "http://o6v28pmb0.bkt.clouddn.com/atta/my_cv.doc";
    }

    public static class FILE {
        /**
         * 文件下载根目录
         */
        public static final String DOWNLOAD_DIR = Environment.getExternalStorageDirectory()
                + java.io.File.separator + "MyCVDownload";
        /**
         * word简历 文件名
         */
        public static final String CV_FILE_NAME = "集美大学梁天德Word简历.doc";

        public static final String FILE_PROVIDER_NAME = "com.tind.liang.fileProvider";
    }

    /**
     * recyclerview item view type
     */
    public static class RvItemType {
        public static final int COUNT_HEADER_VIEW = 1;
        public static final int TYPE_ITEM_VIEW_HEADER = 0;
        public static final int TYPE_ITEM_VIEW_SCROLL_ADAPTER_NORMAL = 1;
        public static final int TYPE_ITEM_VIEW_EDU_ADAPTER_NORMAL = 2;
        public static final int TYPE_ITEM_VIEW_JOB_ADAPTER_NORMAL = 3;
        public static final int TYPE_ITEM_VIEW_HOMEPAGE_ADAPTER_NORMAL = 4;
        public static final int TYPE_ITEM_VIEW_SKILL_ADAPTER_NORMAL = 5;
        public static final int TYPE_ITEM_VIEW_PROJECT_ADAPTER_NORMAL = 6;
        public static final int TYPE_ITEM_VIEW_EVALUATION_ADAPTER_NORMAL = 7;
        public static final int TYPE_ITEM_VIEW_STICKY_ADAPTER_NORMAL = 8;
    }

    /**
     * Intent extra key
     */
    public static class IntentExtra {
        /**
         * 退出当前应用的Intent extra key
         */
        public static final String TAG_EXIT = "exit";

        public static final String TAG_HOMEPAGE_FRAGMENT_URL  ="home_page_fragment_url";
    }
}
