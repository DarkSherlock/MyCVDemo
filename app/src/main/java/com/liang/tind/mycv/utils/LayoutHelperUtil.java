package com.liang.tind.mycv.utils;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.liang.tind.mycv.adapter.FixLayoutAdapter;
import com.liang.tind.mycv.adapter.LinearLayoutAdapter;
import com.liang.tind.mycv.adapter.PersonalHomepageAdapter;
import com.liang.tind.mycv.adapter.ProfessionalSkillAdapter;
import com.liang.tind.mycv.adapter.ProjectIntroductionAdapter;
import com.liang.tind.mycv.adapter.SelfEvaluationAdapter;
import com.liang.tind.mycv.adapter.StickyLayoutAdapter;
import com.liang.tind.mycv.model.CvInfoBean;

import java.util.List;

import static com.liang.tind.mycv.adapter.LinearLayoutAdapter.MODE_EDUCATION_INFO;
import static com.liang.tind.mycv.adapter.LinearLayoutAdapter.MODE_JOB_EXPERIENCE;

/**
 * Created by Sherlock on 2017/9/7.
 */

public class LayoutHelperUtil {

    public static StickyLayoutAdapter initStickyLayoutHelper(@NonNull Context context, @Nullable List<CvInfoBean.CvDetailInfoEntity.BasicInfoEntity> list) {
        StickyLayoutHelper stickyLayoutHelper = new StickyLayoutHelper();
        stickyLayoutHelper.setStickyStart(true);
        return new StickyLayoutAdapter(context, stickyLayoutHelper, list);
    }

    public static LinearLayoutAdapter initLinearLayoutAdapter(@NonNull Context context, @IntRange(from = MODE_JOB_EXPERIENCE, to = MODE_EDUCATION_INFO) int mode) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        //设置间隔高度
        linearLayoutHelper.setDividerHeight(3);
        //设置布局底部与下个布局的间隔
//        linearLayoutHelper.setMarginBottom(20);
        //设置间距
        linearLayoutHelper.setMargin(0, 10, 0, 0);
        return new LinearLayoutAdapter(context, linearLayoutHelper, mode);
    }

    public static PersonalHomepageAdapter initHomepageAdapter(@NonNull Context context, @Nullable String homepage) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        //设置间隔高度
        linearLayoutHelper.setDividerHeight(3);
        //设置布局底部与下个布局的间隔
//        linearLayoutHelper.setMarginBottom(20);
        //设置间距
        linearLayoutHelper.setMargin(0, 10, 0, 0);
        return new PersonalHomepageAdapter(context, linearLayoutHelper, homepage);
    }

    public static ProfessionalSkillAdapter initSkillAdapter(@NonNull Context context,
                                                            @Nullable List<CvInfoBean.CvDetailInfoEntity.ProfessionalSkillsEntity> list) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        //设置间隔高度
        linearLayoutHelper.setDividerHeight(3);

        //设置布局底部与下个布局的间隔
//        linearLayoutHelper.setMarginBottom(10);
        //设置间距
//        linearLayoutHelper.setMargin(0, 10, 0, 0);
        return new ProfessionalSkillAdapter(context, linearLayoutHelper, list);
    }

    public static FixLayoutAdapter initScrollFixLayout(@NonNull Context context, @Nullable View.OnClickListener listener) {
        ScrollFixLayoutHelper scrollFixLayoutHelper = new ScrollFixLayoutHelper(15, 15);
        //show_always:总是显示
        //show_on_enter:当页面滚动到这个视图的位置的时候，才显示
        //show_on_leave:当页面滚出这个视图的位置的时候显示
        scrollFixLayoutHelper.setAlignType(FixLayoutHelper.BOTTOM_RIGHT);// 设置吸边时的基准位置(alignType)
        scrollFixLayoutHelper.setShowType(ScrollFixLayoutHelper.SHOW_ON_ENTER);
        return new FixLayoutAdapter(context, scrollFixLayoutHelper, listener);
    }


    public static ProjectIntroductionAdapter initProjectAdapter(@NonNull Context context,
                                                                @Nullable List<CvInfoBean.CvDetailInfoEntity.ProjectInstructionEntity> list) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        //设置间隔高度
        linearLayoutHelper.setDividerHeight(3);
        //设置布局底部与下个布局的间隔
        linearLayoutHelper.setMarginBottom(10);
        //设置间距
//        linearLayoutHelper.setMargin(0, 10, 0, 0);
        return new ProjectIntroductionAdapter(context, linearLayoutHelper, list);
    }

    public static SelfEvaluationAdapter initSelfEvaluationAdapter(@NonNull Context context, @Nullable String content) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        //设置间隔高度
        linearLayoutHelper.setDividerHeight(3);
        //设置布局底部与下个布局的间隔
        linearLayoutHelper.setMarginBottom(10);
        //设置间距
//        linearLayoutHelper.setMargin(0, 10, 0, 0);
        return new SelfEvaluationAdapter(context, linearLayoutHelper, content);
    }


}
