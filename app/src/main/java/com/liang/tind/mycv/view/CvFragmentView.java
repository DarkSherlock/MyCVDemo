package com.liang.tind.mycv.view;

import com.liang.tind.mycv.model.CvInfoBean;

/**
 * Created by Sherlock on 2017/9/6.
 */

public interface CvFragmentView extends BaseView {
    /**
     * {@link com.liang.tind.mycv.fragment.CvFragment#showCvDetailInfo(CvInfoBean)}
     *
     * @param bean
     */
    void showCvDetailInfo(CvInfoBean bean);
}
