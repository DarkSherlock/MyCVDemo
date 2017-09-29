package com.liang.tind.mycv.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.liang.tind.mycv.R;
import com.liang.tind.mycv.adapter.FixLayoutAdapter;
import com.liang.tind.mycv.adapter.LinearLayoutAdapter;
import com.liang.tind.mycv.adapter.PersonalHomepageAdapter;
import com.liang.tind.mycv.adapter.ProfessionalSkillAdapter;
import com.liang.tind.mycv.adapter.ProjectIntroductionAdapter;
import com.liang.tind.mycv.adapter.SelfEvaluationAdapter;
import com.liang.tind.mycv.adapter.StickyLayoutAdapter;
import com.liang.tind.mycv.component.DaggerCvFragmentComponent;
import com.liang.tind.mycv.model.CvInfoBean;
import com.liang.tind.mycv.module.CvModule;
import com.liang.tind.mycv.presenter.CvFragmentPresenter;
import com.liang.tind.mycv.utils.LayoutHelperUtil;
import com.liang.tind.mycv.utils.ToastUtil;
import com.liang.tind.mycv.view.CvFragmentView;
import com.liang.tind.mycv.view.callback.LoadingCallback;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Sherlock on 2017/8/31.
 */

public class CvFragment extends BaseFragment implements CvFragmentView {
    @Inject
    CvFragmentPresenter mPresenter;
    final List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
    final private List<CvInfoBean.CvDetailInfoEntity.BasicInfoEntity> mBasicInfoEntityList = new ArrayList<>(2);
    final private List<CvInfoBean.CvDetailInfoEntity.ProfessionalSkillsEntity> mSkillEntityList = new ArrayList<>(8);
    final private List<CvInfoBean.CvDetailInfoEntity.ProjectInstructionEntity> mProjectIntroductionList = new ArrayList<>(4);
    private LinearLayoutAdapter mEduLinearLayoutAdapter;
    private LinearLayoutAdapter mJobLinearLayoutAdapter;
    private PersonalHomepageAdapter mHomepageAdapter;
    private FixLayoutAdapter mFixLayoutAdapter;
    private ProjectIntroductionAdapter mProjectAdapter;
    private SelfEvaluationAdapter mSelfEvaluationAdapter;
    private DelegateAdapter mDelegateAdapter;
    private RecyclerView mRecyclerView;
    private View.OnClickListener mGoTopListener;
    private LoadService mLoadService;

    @Override
    protected void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);

        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(9, 10);

        StickyLayoutAdapter stickyLayoutAdapter = LayoutHelperUtil.initStickyLayoutHelper(getContext(), mBasicInfoEntityList);
        mEduLinearLayoutAdapter = LayoutHelperUtil.initLinearLayoutAdapter(getContext(), LinearLayoutAdapter.MODE_EDUCATION_INFO);
        mJobLinearLayoutAdapter = LayoutHelperUtil.initLinearLayoutAdapter(getContext(), LinearLayoutAdapter.MODE_JOB_EXPERIENCE);
        mHomepageAdapter = LayoutHelperUtil.initHomepageAdapter(getContext(), null);
        ProfessionalSkillAdapter skillAdapter = LayoutHelperUtil.initSkillAdapter(getContext(), mSkillEntityList);
        mFixLayoutAdapter = LayoutHelperUtil.initScrollFixLayout(getContext(), mGoTopListener);
        mProjectAdapter = LayoutHelperUtil.initProjectAdapter(getContext(), mProjectIntroductionList);
        mSelfEvaluationAdapter = LayoutHelperUtil.initSelfEvaluationAdapter(getContext(), null);

        adapters.add(stickyLayoutAdapter);
        adapters.add(mHomepageAdapter);
        adapters.add(mJobLinearLayoutAdapter);
        adapters.add(skillAdapter);
        adapters.add(mEduLinearLayoutAdapter);
        adapters.add(mFixLayoutAdapter);
        adapters.add(mProjectAdapter);
        adapters.add(mSelfEvaluationAdapter);

        VirtualLayoutManager manager = new VirtualLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mDelegateAdapter = new DelegateAdapter(manager, true);

//        mDelegateAdapter.setAdapters(adapters);
//        mRecyclerView.setAdapter(mDelegateAdapter);
    }

    @Override
    protected void initData() {
        DaggerCvFragmentComponent.builder()
                .cvModule(new CvModule(this))
                .build()
                .inject(this);

        mGoTopListener = view -> {
//            showToast(" on click go to top btn");
            mRecyclerView.smoothScrollToPosition(0);
        };

        mLoadService = LoadSir.getDefault().register(mRootView, v -> {
            mLoadService.showCallback(LoadingCallback.class);
            mPresenter.loadData(mLoadService);});
        mPresenter.loadData(mLoadService);
    }

    @Override
    protected View getRootView() {
        return mLoadService.getLoadLayout();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cv;
    }


    @Override
    public void showProcess(long process, long max) {

    }

    @Override
    public void dismissProcess() {

    }

    @Override
    public void error() {

    }

    @Override
    public void showProcessDialog() {

    }

    @Override
    public void showToast(String s) {
        ToastUtil.showToast(getContext(), s);
    }

    @Override
    public void showCvDetailInfo(CvInfoBean bean) {
        mBasicInfoEntityList.add(bean.getCvDetailInfo().getBasicInfo());
        mHomepageAdapter.setHomepages(bean.getCvDetailInfo().getHomepages());
        mJobLinearLayoutAdapter.setJobExperienceEntityList(bean.getCvDetailInfo().getJobExperience());
        mSkillEntityList.addAll(bean.getCvDetailInfo().getProfessionalSkills());
        mEduLinearLayoutAdapter.setEducationInfoEntityList(bean.getCvDetailInfo().getEducationInfo());
        mFixLayoutAdapter.setListener(mGoTopListener);
        mProjectIntroductionList.addAll(bean.getCvDetailInfo().getProjectInstruction());
        mSelfEvaluationAdapter.setContent(bean.getCvDetailInfo().getSelfEvaluation());
        notifyDataSetChanged();
    }

    private void notifyDataSetChanged() {
//        mDelegateAdapter.notifyDataSetChanged();
        mDelegateAdapter.setAdapters(adapters);
        mRecyclerView.setAdapter(mDelegateAdapter);
    }
}
