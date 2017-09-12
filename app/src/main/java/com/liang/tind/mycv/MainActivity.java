package com.liang.tind.mycv;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.liang.tind.mycv.activity.BaseActivity;
import com.liang.tind.mycv.component.DaggerComponent;
import com.liang.tind.mycv.fragment.FragmentFactory;
import com.liang.tind.mycv.model.JobInfoBean;
import com.liang.tind.mycv.module.MainModule;
import com.liang.tind.mycv.presenter.MainPresenter;
import com.liang.tind.mycv.utils.ColorUtil;
import com.liang.tind.mycv.utils.OpenFileUtil;
import com.liang.tind.mycv.utils.PackageUtil;
import com.liang.tind.mycv.utils.SnackbarUtil;
import com.liang.tind.mycv.utils.ToastUtil;
import com.liang.tind.mycv.view.MainView;
import com.sunfusheng.glideimageview.GlideImageView;

import java.io.File;

import javax.inject.Inject;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class MainActivity extends BaseActivity implements MainView, NavigationView.OnNavigationItemSelectedListener {
    @Inject
    MainPresenter mPresenter;

    private DrawerLayout mDrawerLayout;

    private Fragment mFragment;
    private View mHeaderView;

    private boolean mIsExit;
    private NavigationView mNavigationView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

//        mToolbar.setLogo(getResources().getDrawable(R.mipmap.ic_launcher_round));
        toolbar.setTitle("");//设置主标题
//        mToolbar.setSubtitle("Subtitle");//设置子标题
        GlideImageView ivToolbarHead = toolbar.findViewById(R.id.iv_icon);
        ivToolbarHead.loadCircleImage(Constant.URL.IMG_HEAD_URL, R.mipmap.ic_launcher);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setCheckedItem(R.id.nav_cv);
        mHeaderView = mNavigationView.getHeaderView(0);
        GlideImageView ivNavHead = mHeaderView.findViewById(R.id.iv_icon);
        ivNavHead.loadCircleImage(Constant.URL.IMG_HEAD_URL, R.mipmap.ic_launcher);

        mFragment = FragmentFactory.getFragment(R.id.nav_cv);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mFragment)
                .commit();
    }

    @Override
    protected void initData() {
        DaggerComponent.builder()
                .mainModule(new MainModule(this))
                .build()
                .inject(this);

        StatusBarUtil.setColorForDrawerLayout(MainActivity.this, mDrawerLayout,
                ColorUtil.getColor(this, R.color.colorPrimary), 0);

        mPresenter.loadData();
    }


    @Override
    public void showProcess() {

    }

    @Override
    public void dismissProcess() {

    }

    @Override
    public void showToast(String s) {
        ToastUtil.showToast(this, s);
    }

    public void switchNavItem(@IdRes int navItemId){
        switchContent(mFragment,FragmentFactory.getFragment(navItemId));
        mNavigationView.setCheckedItem(navItemId);
    }
    @Override
    public void showCVDownloadCompleted() {
        String message = getString(R.string.tip_cv_download_completed);
        Snackbar snackbar = SnackbarUtil.longSnackbar(mDrawerLayout, message, SnackbarUtil.Warning)
                .setActionTextColor(ColorUtil.getColor(this, R.color.colorPrimary))
                .setAction("打开文件", v -> showToast(" 打开文件了~~~"));
        snackbar.show();
    }

    @Override
    public void openWordFile(File file) {
        if (OpenFileUtil.checkEndsWithInStringArray(Constant.FILE.CV_FILE_NAME, getResources().
                getStringArray(R.array.fileEndingWord))) {
            Intent intent = OpenFileUtil.getWordFileIntent(file);
            boolean intentAvailable = OpenFileUtil.isIntentAvailable(this, intent);
            if (intentAvailable) {
                startActivity(intent);
            } else {
                showToast(getString(R.string.tip_install_open_word_app));
            }

        } else {
            showToast(getString(R.string.tip_no_support_open_file));
        }
    }

    @Override
    public void showOpenWordFileDialog(File file) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.warm_tip))
                .setMessage(R.string.tip_open_word_file_or_not)
                .setPositiveButton(R.string.confirm, (dialogInterface, i) -> openWordFile(file))
                .setNegativeButton(R.string.cancle, null)
                .create();
        dialog.show();
    }

    @Override
    public void showJobInfo(JobInfoBean bean) {
        TextView tvCompanyName = mHeaderView.findViewById(R.id.tv_company_name);
        TextView tvJobName = mHeaderView.findViewById(R.id.tv_job_name);
//        TextView tvJobTime = mHeaderView.findViewById(R.id.tv_job_time);
        JobInfoBean.JobInfoEntity jobInfo = bean.getJobInfo();
        tvCompanyName.setText(jobInfo.getCompanyName());
        tvJobName.setText(jobInfo.getJobName());
//        tvJobTime.setText(jobInfo.getJobTime());
    }

    private void showCallPhoneDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.warm_tip))
                .setMessage(R.string.tip_call_phone_or_not)
                .setPositiveButton(R.string.confirm, (dialogInterface, i) -> requestPermission())
                .setNegativeButton(R.string.cancle, null)
                .create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //mainactivity 指定launchMode="singleTask",需要退出的时候只需发一个intent过来即可。
        if (intent != null) {
            boolean isExit = intent.getBooleanExtra(Constant.IntentExtra.TAG_EXIT, false);
            if (isExit) this.finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_cv:
                switchContent(mFragment, FragmentFactory.getFragment(id));
                break;
            case R.id.nav_githup:
                switchContent(mFragment, FragmentFactory.getFragment(id));
                break;
            case R.id.nav_csdn:
                switchContent(mFragment, FragmentFactory.getFragment(id));
                break;
            case R.id.nav_jianshu:
                switchContent(mFragment, FragmentFactory.getFragment(id));
                break;
            case R.id.nav_word:
                mPresenter.downloadCV();
                break;
            case R.id.nav_phone:
                showCallPhoneDialog();
                break;
            case R.id.nav_we_chat:
                switchContent(mFragment, FragmentFactory.getFragment(id));
                break;
            case R.id.nav_qq:
                if (PackageUtil.isAppAvilible(this, PackageUtil.QQ_PACKAGE_NAME)) {
//                    Intent intent = PackageUtil.getOtherAppIntent(PackageUtil.QQ_PACKAGE_NAME, PackageUtil.QQ_LAUNCHER);
                    Intent intent = PackageUtil.getQQIntent();
                    startActivity(intent);
                } else {
                    showToast(getString(R.string.tip_no_install_qq));
                }
                break;
        }
        assert mDrawerLayout != null;
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void switchContent(Fragment from, Fragment to) {
        if (mFragment != to && to != null) {
            mFragment = to;
            FragmentManager fm = getSupportFragmentManager();
            //添加渐隐渐现的动画
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_right_exit);
            if (!to.isAdded()) {    // 先判断是否被add过
                ft.hide(from).add(R.id.fragment_container, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                ft.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission() {
        //申请权限
        MainActivityPermissionsDispatcher.callPhoneWithCheck(this);
    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void callPhone() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:15659827282"));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.CALL_PHONE)
    void showRationale(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.request_call_permission)
                .setMessage(R.string.tip_rationale_request_call_permission)
                .setPositiveButton(R.string.confirm, (dialog, which) -> {
                    //再次执行请求
                    request.proceed();
                })
                .show();
    }

    @OnPermissionDenied(Manifest.permission.CALL_PHONE)
    void permissionDenied() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.warm_tip)
                .setMessage(R.string.tip_can_reset_permission_in_setting)
                .setPositiveButton(R.string.confirm, null)
                .show();
    }

    @OnNeverAskAgain(Manifest.permission.CALL_PHONE)
    void neverAsk() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.warm_tip)
                .setMessage(R.string.tip_can_reset_permission_in_setting)
                .setPositiveButton(R.string.confirm, null)
                .show();
    }



    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (mIsExit) {
            this.finish();
        } else {
            if (mDrawerLayout.isDrawerOpen(Gravity.START)){
                mDrawerLayout.closeDrawer(Gravity.START);
            }else {
                showToast(getString(R.string.tip_exit_once_more));
                mIsExit = true;
                new Handler().postDelayed(() -> mIsExit = false, 2000);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentFactory.onActivityDestroyed();
        mPresenter.onDetachView();
    }
}
