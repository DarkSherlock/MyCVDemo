package com.liang.tind.mycv.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.liang.tind.mycv.R;

import static com.liang.tind.mycv.Constant.RvItemType.COUNT_HEADER_VIEW;
import static com.liang.tind.mycv.Constant.RvItemType.TYPE_ITEM_VIEW_HEADER;
import static com.liang.tind.mycv.Constant.RvItemType.TYPE_ITEM_VIEW_HOMEPAGE_ADAPTER_NORMAL;


/**
 * Created by admin on 2017/5/17.
 */

public class PersonalHomepageAdapter extends DelegateAdapter.Adapter<RecyclerView.ViewHolder> {
    public Context context;
    private LayoutHelper helper;
    private LayoutInflater inflater;
    private String mHomepages;

    public PersonalHomepageAdapter(@NonNull Context context, @NonNull LayoutHelper helper, @Nullable String homepages) {
        this.inflater = LayoutInflater.from(context);
        this.helper = helper;
        this.context = context;
        this.mHomepages = homepages;
    }

    public void setHomepages(@Nullable String homepages) {
        mHomepages = homepages;
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
                return new LinearLayoutAdapter.HeaderViewHolder(inflater.inflate(R.layout.item_header_view_cv_fragment_rv,
                        parent, false));
            case TYPE_ITEM_VIEW_HOMEPAGE_ADAPTER_NORMAL:
                return new NormalViewHolder(inflater.inflate(R.layout.item_personal_homepage_cv_fragment_rv,
                        parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof LinearLayoutAdapter.HeaderViewHolder && position == 0) {
            LinearLayoutAdapter.HeaderViewHolder headerViewHolder = (LinearLayoutAdapter.HeaderViewHolder) holder;
            headerViewHolder.tvTitle.setText(context.getString(R.string.personal_homepage));
        } else if (holder instanceof NormalViewHolder && position != 0) {
            NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
            if (mHomepages != null)
                normalViewHolder.tvContent.setText(mHomepages);
        }
    }

    @Override
    public int getItemCount() {
        return TextUtils.isEmpty(mHomepages) ? 0 : 1 + COUNT_HEADER_VIEW;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ITEM_VIEW_HEADER;
        } else {
            return TYPE_ITEM_VIEW_HOMEPAGE_ADAPTER_NORMAL;
        }
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        public TextView tvContent;

        public NormalViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

}
