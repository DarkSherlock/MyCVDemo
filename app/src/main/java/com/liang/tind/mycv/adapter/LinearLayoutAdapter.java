package com.liang.tind.mycv.adapter;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.liang.tind.mycv.R;
import com.liang.tind.mycv.model.CvInfoBean;

import java.util.List;

import static com.liang.tind.mycv.Constant.RvItemType.COUNT_HEADER_VIEW;
import static com.liang.tind.mycv.Constant.RvItemType.TYPE_ITEM_VIEW_EDU_ADAPTER_NORMAL;
import static com.liang.tind.mycv.Constant.RvItemType.TYPE_ITEM_VIEW_HEADER;
import static com.liang.tind.mycv.Constant.RvItemType.TYPE_ITEM_VIEW_JOB_ADAPTER_NORMAL;

/**
 * Created by admin on 2017/5/17.
 */

public class LinearLayoutAdapter extends DelegateAdapter.Adapter<RecyclerView.ViewHolder> {
    public Context context;
    private LayoutHelper helper;
    private LayoutInflater inflater;
    private List<CvInfoBean.CvDetailInfoEntity.EducationInfoEntity> mEducationInfoEntityList;
    private List<CvInfoBean.CvDetailInfoEntity.JobExperienceEntity> mJobExperienceEntityList;
    public static final int MODE_JOB_EXPERIENCE = 0;
    public static final int MODE_EDUCATION_INFO = 1;


    private int mode;

    public LinearLayoutAdapter(@NonNull Context context, @NonNull LayoutHelper helper,
                               @IntRange(from = MODE_JOB_EXPERIENCE, to = MODE_EDUCATION_INFO) int mode) {
        this.inflater = LayoutInflater.from(context);
        this.helper = helper;
        this.context = context;
        this.mode = mode;
    }

    public void setEducationInfoEntityList(@Nullable List<CvInfoBean.CvDetailInfoEntity.EducationInfoEntity> educationInfoEntityList) {
        mEducationInfoEntityList = educationInfoEntityList;
//        notifyDataSetChanged();
    }

    public void setJobExperienceEntityList(@Nullable List<CvInfoBean.CvDetailInfoEntity.JobExperienceEntity> jobExperienceEntityList) {
        mJobExperienceEntityList = jobExperienceEntityList;
//        notifyDataSetChanged();
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return this.helper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ITEM_VIEW_HEADER:
                return new HeaderViewHolder(inflater.inflate(R.layout.item_header_view_cv_fragment_rv,
                        parent, false));
            case TYPE_ITEM_VIEW_EDU_ADAPTER_NORMAL:
            case TYPE_ITEM_VIEW_JOB_ADAPTER_NORMAL:
                return new NormalViewHolder(inflater.inflate(R.layout.item_job_and_edu_cv_fragment_rv,
                        parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String headerTitle = "";
        String normalSubtitle1 = "";
        String normalSubtitle2 = "";
        String normalSubtitle3 = "";
        String normalContent = "";
        if (mode == MODE_EDUCATION_INFO) {
            if (position == 0) {
                headerTitle = context.getString(R.string.edu_background);
            } else {
                CvInfoBean.CvDetailInfoEntity.EducationInfoEntity educationInfoEntity = mEducationInfoEntityList
                        .get(position - COUNT_HEADER_VIEW);

                normalSubtitle1 = educationInfoEntity.getTimeRange();
                normalSubtitle2 = educationInfoEntity.getSchoolName();
                normalSubtitle3 = educationInfoEntity.getDegree();
                normalContent = educationInfoEntity.getEduDesc();
            }

        } else if (mode == MODE_JOB_EXPERIENCE) {
            if (position == 0) {
                headerTitle = context.getString(R.string.job_experience);
            } else {
                CvInfoBean.CvDetailInfoEntity.JobExperienceEntity jobExperienceEntity = mJobExperienceEntityList
                        .get(position - COUNT_HEADER_VIEW);

                normalSubtitle1 = jobExperienceEntity.getTimeRange();
                normalSubtitle2 = jobExperienceEntity.getCampanyName();
                normalSubtitle3 = jobExperienceEntity.getJobName();
                normalContent = jobExperienceEntity.getJobDesc();
            }

        } else {
            throw new IllegalArgumentException("mode err");
        }

        if (holder instanceof HeaderViewHolder && position == 0) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.tvTitle.setText(headerTitle);
        } else if (holder instanceof NormalViewHolder && position != 0) {
            NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
            normalViewHolder.tvSubtitle1.setText(normalSubtitle1);
            normalViewHolder.tvSubtitle2.setText(normalSubtitle2);
            normalViewHolder.tvSubtitle3.setText(normalSubtitle3);
            normalViewHolder.tvContent.setText(normalContent);
        }
    }

    @Override
    public int getItemCount() {
        int count;
        if (mode == MODE_EDUCATION_INFO) {
            count = mEducationInfoEntityList == null ? 0 : mEducationInfoEntityList.size() + COUNT_HEADER_VIEW;//1 是head view的数量
        } else if (mode == MODE_JOB_EXPERIENCE) {
            count = mJobExperienceEntityList == null ? 0 : mJobExperienceEntityList.size() + COUNT_HEADER_VIEW;
        } else {
            throw new IllegalArgumentException("mode err");
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ITEM_VIEW_HEADER;
        } else {
            return mode == MODE_EDUCATION_INFO ? TYPE_ITEM_VIEW_EDU_ADAPTER_NORMAL : TYPE_ITEM_VIEW_JOB_ADAPTER_NORMAL;
        }
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSubtitle1;
        public TextView tvSubtitle2;
        public TextView tvSubtitle3;
        public TextView tvContent;

        public NormalViewHolder(View itemView) {
            super(itemView);
            tvSubtitle1 = itemView.findViewById(R.id.tv_subtitle1);
            tvSubtitle2 = itemView.findViewById(R.id.tv_subtitle2);
            tvSubtitle3 = itemView.findViewById(R.id.tv_subtitle3);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView ivArrow;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivArrow = itemView.findViewById(R.id.iv_arrow);
        }
    }
}
