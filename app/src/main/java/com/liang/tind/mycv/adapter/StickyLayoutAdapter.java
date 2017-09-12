package com.liang.tind.mycv.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.liang.tind.mycv.Constant;
import com.liang.tind.mycv.R;
import com.liang.tind.mycv.model.CvInfoBean;
import com.sunfusheng.glideimageview.GlideImageView;

import java.util.List;

import static com.liang.tind.mycv.Constant.RvItemType.TYPE_ITEM_VIEW_STICKY_ADAPTER_NORMAL;

/**
 * Created by admin on 2017/5/17.
 */

public class StickyLayoutAdapter extends DelegateAdapter.Adapter<StickyLayoutAdapter.BasicInfoViewHolder> {
    public Context context;
    private LayoutHelper helper;
    private LayoutInflater inflater;
    private List<CvInfoBean.CvDetailInfoEntity.BasicInfoEntity> mList;

    public StickyLayoutAdapter(@NonNull Context context, @NonNull LayoutHelper helper, @Nullable List<CvInfoBean.CvDetailInfoEntity.BasicInfoEntity> list) {
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
    public BasicInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BasicInfoViewHolder(inflater.inflate(R.layout.item_basic_info_cv_fragment_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(BasicInfoViewHolder holder, int position) {
        CvInfoBean.CvDetailInfoEntity.BasicInfoEntity basicInfoEntity = mList.get(position);
        holder.tvName.setText(basicInfoEntity.getName());
        holder.tvEmail.setText(basicInfoEntity.getEmail());
        holder.tvJobName.setText(basicInfoEntity.getJobIntention());
        holder.tvPhone.setText(basicInfoEntity.getPhone());
        holder.ivHead.loadImage(Constant.URL.IMG_HEAD_URL, R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM_VIEW_STICKY_ADAPTER_NORMAL;
    }

    public static class BasicInfoViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvPhone;
        public TextView tvEmail;
        public TextView tvJobName;
        private final GlideImageView ivHead;

        public BasicInfoViewHolder(View itemView) {
            super(itemView);
            ivHead = itemView.findViewById(R.id.iv_icon);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            tvEmail = (TextView) itemView.findViewById(R.id.tv_email);
            tvJobName = (TextView) itemView.findViewById(R.id.tv_job_name);
        }
    }
}
