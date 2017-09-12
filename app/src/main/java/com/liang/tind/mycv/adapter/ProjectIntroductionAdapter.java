package com.liang.tind.mycv.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.liang.tind.mycv.R;
import com.liang.tind.mycv.model.CvInfoBean;
import com.liang.tind.mycv.utils.ColorUtil;

import java.util.List;

import static com.liang.tind.mycv.Constant.RvItemType.COUNT_HEADER_VIEW;
import static com.liang.tind.mycv.Constant.RvItemType.TYPE_ITEM_VIEW_HEADER;
import static com.liang.tind.mycv.Constant.RvItemType.TYPE_ITEM_VIEW_PROJECT_ADAPTER_NORMAL;

/**
 * Created by admin on 2017/5/17.
 */

public class ProjectIntroductionAdapter extends DelegateAdapter.Adapter<RecyclerView.ViewHolder> {
    public Context context;
    private LayoutHelper helper;
    private LayoutInflater inflater;
    private List<CvInfoBean.CvDetailInfoEntity.ProjectInstructionEntity> mList;
    public ProjectIntroductionAdapter(@NonNull Context context, @NonNull LayoutHelper helper,
                                      @Nullable List<CvInfoBean.CvDetailInfoEntity.ProjectInstructionEntity> list) {
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
            case TYPE_ITEM_VIEW_PROJECT_ADAPTER_NORMAL:
                return new ProjectViewHolder(inflater.inflate(R.layout.item_project_introduction_cv_fragment_rv,
                        parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof LinearLayoutAdapter.HeaderViewHolder && position == 0) {
            LinearLayoutAdapter.HeaderViewHolder headerViewHolder = (LinearLayoutAdapter.HeaderViewHolder) holder;
            headerViewHolder.tvTitle.setText(context.getString(R.string.project_introduction));
        } else if (holder instanceof ProjectViewHolder && position != 0) {
            CvInfoBean.CvDetailInfoEntity.ProjectInstructionEntity entity = mList.get(position - COUNT_HEADER_VIEW);
            ProjectViewHolder normalViewHolder = (ProjectViewHolder) holder;
            normalViewHolder.mTvProjectName.setText(entity.getProjectName());
            normalViewHolder.mTvCopyright.setText(entity.getCopyRight());
            normalViewHolder.mTvDevTool.setText(entity.getDevTool());
            normalViewHolder.mTvProjectDesc.setText(entity.getProjectDesc());
            normalViewHolder.mTvProjectTec.setText(entity.getProjectTechnology());

            String downloadUrl = entity.getDownloadUrl();
            SpannableString smp = new SpannableString(downloadUrl);

            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Uri uri = Uri.parse(downloadUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    context.startActivity(intent);
                }
            } ;
            //设置点击
            smp.setSpan(clickableSpan, 0 , downloadUrl.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置前景色
            smp.setSpan(new ForegroundColorSpan(ColorUtil.getColor(context,R.color.selected_blue)),
                    0, downloadUrl.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            normalViewHolder.mTvDownloadUrl.setMovementMethod(LinkMovementMethod.getInstance());
            normalViewHolder.mTvDownloadUrl.setText(smp);
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
            return TYPE_ITEM_VIEW_PROJECT_ADAPTER_NORMAL;
        }
    }

    public static class ProjectViewHolder extends RecyclerView.ViewHolder{

        public final TextView mTvProjectName;
        public final TextView mTvCopyright;
        public final TextView mTvProjectDesc;
        public final TextView mTvProjectTec;
        public final TextView mTvDevTool;
        public final TextView mTvDownloadUrl;

        public ProjectViewHolder(View itemView) {
            super(itemView);
            mTvProjectName = itemView.findViewById(R.id.tv_project_name);
            mTvDevTool = itemView.findViewById(R.id.tv_dev_tool);
            mTvCopyright = itemView.findViewById(R.id.tv_copyright);
            mTvProjectDesc = itemView.findViewById(R.id.tv_project_desc);
            mTvProjectTec = itemView.findViewById(R.id.tv_project_technology);
            mTvDownloadUrl = itemView.findViewById(R.id.tv_download_url);
        }
    }
}
