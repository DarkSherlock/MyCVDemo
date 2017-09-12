package com.liang.tind.mycv.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.liang.tind.mycv.R;
import com.liang.tind.mycv.model.CvInfoBean;

import java.util.List;

import static com.liang.tind.mycv.Constant.RvItemType.COUNT_HEADER_VIEW;
import static com.liang.tind.mycv.Constant.RvItemType.TYPE_ITEM_VIEW_HEADER;
import static com.liang.tind.mycv.Constant.RvItemType.TYPE_ITEM_VIEW_SKILL_ADAPTER_NORMAL;

/**
 * Created by admin on 2017/5/17.
 */

public class ProfessionalSkillAdapter extends DelegateAdapter.Adapter<RecyclerView.ViewHolder> {
    public Context context;
    private LayoutHelper helper;
    private LayoutInflater inflater;
    private List<CvInfoBean.CvDetailInfoEntity.ProfessionalSkillsEntity> mList;

    public ProfessionalSkillAdapter(@NonNull Context context, @NonNull LayoutHelper helper,
                                    @Nullable List<CvInfoBean.CvDetailInfoEntity.ProfessionalSkillsEntity> list) {
        this.inflater = LayoutInflater.from(context);
        this.helper = helper;
        this.context = context;
        this.mList = list;
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return this.helper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ITEM_VIEW_HEADER:
                return new LinearLayoutAdapter.HeaderViewHolder(inflater.inflate(R.layout.item_header_view_cv_fragment_rv,
                        parent, false));
            case TYPE_ITEM_VIEW_SKILL_ADAPTER_NORMAL:
                return new PersonalHomepageAdapter.NormalViewHolder(inflater.inflate(R.layout.item_perssional_skill_cv_fragment_rv,
                        parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof LinearLayoutAdapter.HeaderViewHolder && position == 0) {
            LinearLayoutAdapter.HeaderViewHolder headerViewHolder = (LinearLayoutAdapter.HeaderViewHolder) holder;
            headerViewHolder.tvTitle.setText(context.getString(R.string.professional_skills));
        } else if (holder instanceof PersonalHomepageAdapter.NormalViewHolder && position != 0) {
            CvInfoBean.CvDetailInfoEntity.ProfessionalSkillsEntity entity = mList.get(position - COUNT_HEADER_VIEW);
            PersonalHomepageAdapter.NormalViewHolder normalViewHolder = (PersonalHomepageAdapter.NormalViewHolder) holder;
            normalViewHolder.tvContent.setText(entity.getSkillDesc());
        }
    }

    @Override
    public int getItemCount() {
        return mList ==null?0:mList.size()+COUNT_HEADER_VIEW;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ITEM_VIEW_HEADER;
        } else {
            return TYPE_ITEM_VIEW_SKILL_ADAPTER_NORMAL;
        }
    }

}
