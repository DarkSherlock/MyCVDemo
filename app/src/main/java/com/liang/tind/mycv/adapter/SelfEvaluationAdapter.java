package com.liang.tind.mycv.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.liang.tind.mycv.R;

import static com.liang.tind.mycv.Constant.RvItemType.COUNT_HEADER_VIEW;
import static com.liang.tind.mycv.Constant.RvItemType.TYPE_ITEM_VIEW_EVALUATION_ADAPTER_NORMAL;
import static com.liang.tind.mycv.Constant.RvItemType.TYPE_ITEM_VIEW_HEADER;

/**
 * Created by admin on 2017/5/17.
 */

public class SelfEvaluationAdapter extends DelegateAdapter.Adapter<RecyclerView.ViewHolder> {
    public Context context;
    private LayoutHelper helper;
    private LayoutInflater inflater;
    private String mContent;

    public SelfEvaluationAdapter(@NonNull Context context, @NonNull LayoutHelper helper,
                                 @Nullable String content) {
        this.inflater = LayoutInflater.from(context);
        this.helper = helper;
        this.context = context;
        this.mContent = content;
    }

    public void setContent(String content) {
        mContent = content;
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
            case TYPE_ITEM_VIEW_EVALUATION_ADAPTER_NORMAL:
                return new PersonalHomepageAdapter.NormalViewHolder(inflater.inflate(R.layout.item_perssional_skill_cv_fragment_rv,
                        parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof LinearLayoutAdapter.HeaderViewHolder && position == 0) {
            LinearLayoutAdapter.HeaderViewHolder headerViewHolder = (LinearLayoutAdapter.HeaderViewHolder) holder;
            headerViewHolder.tvTitle.setText(context.getString(R.string.self_evaluation));
        } else if (holder instanceof PersonalHomepageAdapter.NormalViewHolder && position != 0) {
            PersonalHomepageAdapter.NormalViewHolder normalViewHolder = (PersonalHomepageAdapter.NormalViewHolder) holder;
            if( !TextUtils.isEmpty(mContent))
            normalViewHolder.tvContent.setText(mContent);
        }
    }

    @Override
    public int getItemCount() {
        return TextUtils.isEmpty(mContent)?0:1+COUNT_HEADER_VIEW;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ITEM_VIEW_HEADER;
        } else {
            return TYPE_ITEM_VIEW_EVALUATION_ADAPTER_NORMAL;
        }
    }

}
