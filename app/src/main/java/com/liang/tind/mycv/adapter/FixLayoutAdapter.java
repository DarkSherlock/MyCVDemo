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

/**
 * Created by Sherlock on 2017/9/6.
 */

public class FixLayoutAdapter extends DelegateAdapter.Adapter<FixLayoutAdapter.ScrollFixViewHolder> {
    private Context mContext;
    private LayoutHelper mHelper;
    private View.OnClickListener mListener;

    public FixLayoutAdapter(@NonNull Context context, @NonNull LayoutHelper helper, @Nullable View.OnClickListener listener) {
        mContext = context;
        mHelper = helper;
        mListener = listener;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mHelper;
    }

    @Override
    public ScrollFixViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ScrollFixViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_scroll_fix_cv_fragment_rv, null));
    }

    @Override
    public void onBindViewHolder(ScrollFixViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return Constant.RvItemType.TYPE_ITEM_VIEW_SCROLL_ADAPTER_NORMAL;
    }

    public void setListener(View.OnClickListener listener) {
        mListener = listener;

    }

    public class ScrollFixViewHolder extends RecyclerView.ViewHolder {

        private final TextView mButton;

        public ScrollFixViewHolder(View itemView) {
            super(itemView);
            mButton = itemView.findViewById(R.id.btn_go_to_top);
            mButton.setOnClickListener(mListener);
        }

    }
}
