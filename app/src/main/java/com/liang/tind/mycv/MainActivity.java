package com.liang.tind.mycv;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.liang.tind.mycv.activity.BaseActivity;
import com.liang.tind.mycv.component.DaggerComponent;
import com.liang.tind.mycv.fragment.FragmentFactory;
import com.liang.tind.mycv.http.RxBus;
import com.liang.tind.mycv.model.JobInfoBean;
import com.liang.tind.mycv.module.MainModule;
import com.liang.tind.mycv.presenter.MainPresenter;
import com.liang.tind.mycv.utils.AlertDialogUtil;
import com.liang.tind.mycv.utils.FilesUtils;
import com.liang.tind.mycv.utils.IntentUtil;
import com.liang.tind.mycv.utils.OpenFileUtil;
import com.liang.tind.mycv.utils.PackageUtil;
import com.liang.tind.mycv.utils.SnackbarUtil;
import com.liang.tind.mycv.utils.ToastUtil;
import com.liang.tind.mycv.utils.UIUtils;
import com.liang.tind.mycv.view.MainView;
import com.liang.tind.mycv.widget.specialprolibrary.SpecialProgressBarView;
import com.sunfusheng.glideimageview.GlideImageView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

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
    private Intent mIntent;
    private SpecialProgressBarView mProgressBarView;
    private Dialog mProcessDialog;

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
        ivToolbarHead.loadCircleImage(Constant.URL.IMG_HEAD_URL, R.mipmap.image_head);

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
        ivNavHead.loadCircleImage(Constant.URL.IMG_HEAD_URL, R.mipmap.image_head);

        disableNavigationViewScrollbars(mNavigationView);

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
                ContextCompat.getColor(this, R.color.colorPrimary), 0);

        mPresenter.loadData(null);
        initProcessDialog();
    }


    @Override
    public void showProcess(long process, long total) {
        if (mProgressBarView.getMax() == 1) {
            mProgressBarView.setMax(total);
        }

//        if (process >= total / 2) {
//            Looper.prepare();
//
//            mProgressBarView.setError();
//            Looper.loop();
//        }
        mProgressBarView.setProgress(process);
    }

    @Override
    public void dismissProcess() {
    }

    @Override
    public void error() {
        mProgressBarView.setError();
    }

    @Override
    public void showProcessDialog() {
        mProcessDialog.show();
        mProgressBarView.beginStarting();
    }

    @NonNull
    private Dialog initProcessDialog() {
        mProcessDialog = new Dialog(this, R.style.AlertDialogStyle);
        View contentView = getLayoutInflater().inflate(R.layout.view_download_process_dialog, null);
        mProgressBarView = contentView.findViewById(R.id.ls);
        mProgressBarView.setEndSuccessBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))//设置进度完成时背景颜色
                .setCanDragChangeProgress(false)//是否进度条是否可以拖拽
                .setCanReBack(true)//是否在进度成功后返回初始状态
                .setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                        getResources().getDisplayMetrics()));//设置字体大小

        mProgressBarView.setOntextChangeListener(new SpecialProgressBarView.OntextChangeListener() {
            @Override
            public String onProgressTextChange(SpecialProgressBarView specialProgressBarView, long max, long progress) {
                return progress * 100 / max + "%";
            }

            @Override
            public String onErrorTextChange(SpecialProgressBarView specialProgressBarView, long max, long progress) {
                return "error";
            }

            @Override
            public String onSuccessTextChange(SpecialProgressBarView specialProgressBarView, long max, long progress) {
                return "done";
            }
        });

        mProgressBarView.setOnAnimationEndListener(() -> {
            mPresenter.startDownloadCV(new Subscriber<MainView>() {
                Subscription mSubscription;
                @Override
                public void onSubscribe(Subscription s) {
                    s.request(1);
                    mSubscription = s;
                    RxBus.getInstance().addSubscription(MainActivity.this,s);
                }

                @Override
                public void onNext(MainView mainView) {
                    mainView.error();
                    RxBus.getInstance().unSubscribe(MainActivity.this);
                }

                @Override
                public void onError(Throwable t) {

                }

                @Override
                public void onComplete() {

                }
            });
        });

        mProgressBarView.setStateChangedAnimationEndListener(new SpecialProgressBarView.StateChangedAnimationEndListener() {
            @Override
            public void onErrorAnimationEnd() {
                mProcessDialog.dismiss();
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("下载简历发生错误!")
                        .setMessage("是否重新下载?")
                        .setPositiveButton(R.string.re_download, (dialogInterface, i) -> mPresenter.downloadCV())
                        .setNegativeButton(R.string.dont, null).show();
                AlertDialogUtil.changeTitleAndMessageColor(MainActivity.this, dialog);
            }

            @Override
            public void onSuccessAnimationEnd() {
                new Handler(getMainLooper()).postDelayed(() -> {
                    mProcessDialog.dismiss();
                    mProgressBarView.setBackgroundResource(R.drawable.bg_corner_10_color_primary);
                    showCVDownloadCompleted();
                    setProcessDialogAniamtion(contentView);
                }, 300);

            }
        });
        mProcessDialog.setContentView(contentView, UIUtils.getParams(this));
        return mProcessDialog;
    }

    private void setProcessDialogAniamtion(View contentView) {
//        ViewAnimatorH
    }

    @Override
    public void showToast(String s) {
        ToastUtil.showToast(this, s);
    }

    public void switchNavItem(@IdRes int navItemId) {
        switchContent(mFragment, FragmentFactory.getFragment(navItemId));
        mNavigationView.setCheckedItem(navItemId);
    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    @Override
    public void showCVDownloadCompleted() {
        String message = getString(R.string.tip_cv_download_completed);
        Snackbar snackbar = SnackbarUtil.longSnackbar(mDrawerLayout, message, SnackbarUtil.Warning)
                .setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setAction("打开文件", v -> openWordFile(new File(Constant.FILE.DOWNLOAD_DIR,
                        Constant.FILE.CV_FILE_NAME)));
        snackbar.show();
    }

    @Override
    public void openWordFile(File file) {
        if (OpenFileUtil.checkEndsWithInStringArray(Constant.FILE.CV_FILE_NAME, getResources().
                getStringArray(R.array.fileEndingWord))) {
            mIntent = OpenFileUtil.getWordFileIntent(this, file);
            boolean intentAvailable = OpenFileUtil.isIntentAvailable(this, mIntent);
            if (intentAvailable) {
                checkReadStoragePermission();

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
        AlertDialogUtil.changeTitleAndMessageColor(this, dialog);
    }

    @Override
    public void showJobInfo(JobInfoBean bean) {
        TextView tvCompanyName = mHeaderView.findViewById(R.id.tv_company_name);
        TextView tvJobName = mHeaderView.findViewById(R.id.tv_job_name);
//        TextView tvJobTime = mHeaderView.findViewById(R.id.tv_job_time);
        tvCompanyName.setText(bean.getCompanyName());
        tvJobName.setText(bean.getJobName());
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
        AlertDialogUtil.changeTitleAndMessageColor(this, dialog);
    }

    private void showDownloadDialog() {
        File file = new File(Constant.FILE.DOWNLOAD_DIR, Constant.FILE.CV_FILE_NAME);
        if (file.exists()) {
            showOpenWordFileDialog(file);
        } else {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.warm_tip))
                    .setMessage(R.string.tip_download_cv_or_not)
                    .setPositiveButton(R.string.confirm, (dialogInterface, i) -> checkWriteStoragePermission())
                    .setNegativeButton(R.string.dont, null)
                    .create();
            dialog.show();
            AlertDialogUtil.changeTitleAndMessageColor(this, dialog);
        }

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
            case R.id.action_delete_cv:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("您将删除已下载的简历")
                        .setMessage("确定删除吗？")
                        .setPositiveButton("确定", (dialogInterface, i) -> {
                            boolean result = FilesUtils.deleteFile(new File(Constant.FILE.DOWNLOAD_DIR));
                            dialogInterface.dismiss();
                            if (result) {
                                showToast("下载的简历已删除!");
                            } else {
                                showToast("删除失败，请检查文件是否存在!");
                            }
                        })
                        .setNegativeButton("取消", (dialogInterface, i) -> dialogInterface.dismiss())
                        .create();
                dialog.show();
                AlertDialogUtil.changeTitleAndMessageColor(this, dialog);
                break;
            case R.id.action_share:
                IntentUtil.startShareIntent(this);
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
                showDownloadDialog();
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
        Log.e(TAG, "requestPermission: ");
    }


    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void callPhone() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:15659827282"));
        startActivity(intent);
    }

    @OnShowRationale(Manifest.permission.CALL_PHONE)
    void showRationale(final PermissionRequest request) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.request_call_permission)
                .setMessage(R.string.tip_rationale_request_call_permission)
                .setPositiveButton(R.string.confirm, (alertDialog, which) -> {
                    //再次执行请求
                    request.proceed();
                })
                .setNegativeButton(R.string.dont, null)
                .show();
        AlertDialogUtil.changeTitleAndMessageColor(this, dialog);
    }

    @OnPermissionDenied(Manifest.permission.CALL_PHONE)
    void permissionDenied() {
        showAskGivePermissionAgain();
        Log.e(TAG, "permissionDenied: ");
    }

    @OnNeverAskAgain(Manifest.permission.CALL_PHONE)
    void neverAsk() {
        showAskGivePermissionAgain();
        Log.e(TAG, "neverAsk: ");
    }

    /**
     * ———————————————————————读写SD卡权限———————————————————————start
     */
    private void checkWriteStoragePermission() {
        MainActivityPermissionsDispatcher.requestWriteStoragePermissionWithCheck(this);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void requestWriteStoragePermission() {
        mPresenter.downloadCV();
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void requestWriteStorageRationale(final PermissionRequest request) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.request_storage_permission)
                .setMessage(R.string.tip_rationale_request_write_storage)
                .setPositiveButton(R.string.confirm, (alertDialog, which) -> {
                    //再次执行请求
                    request.proceed();
                })
                .setNegativeButton(R.string.dont, null)
                .show();
        AlertDialogUtil.changeTitleAndMessageColor(this, dialog);
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void requestWriteStorageDenied() {
        showAskGivePermissionAgain();
        Log.e(TAG, "requestWriteStorageDenied: ");
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void requestWriteStorageNever() {
        showAskGivePermissionAgain();
        Log.e(TAG, "requestWriteStorageNever: ");
    }


    private void checkReadStoragePermission() {
        MainActivityPermissionsDispatcher.readStoragePermissionWithCheck(this);
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void readStoragePermission() {
        startActivity(mIntent);
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void readStorageRationale(final PermissionRequest request) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.request_storage_permission)
                .setMessage(R.string.tip_rationale_request_read_storage)
                .setPositiveButton(R.string.confirm, (alertDialog, which) -> {
                    //再次执行请求
                    request.proceed();
                })
                .setNegativeButton(R.string.dont, null)
                .show();
        AlertDialogUtil.changeTitleAndMessageColor(this, dialog);
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void readStorageDenied() {
        showAskGivePermissionAgain();
        Log.e(TAG, "requestWriteStorageNever: ");
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    void readStorageNever() {
        showAskGivePermissionAgain();
        Log.e(TAG, "requestWriteStorageNever: ");
    }

    private void showAskGivePermissionAgain() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.tip_can_reset_permission_in_setting)
                .setMessage(R.string.tip_give_permission_in_setting)
                .setPositiveButton(R.string.confirm, (dialogInterface, i) -> {
                    IntentUtil.getAppDetailSettingIntent(this);
                })
                .setNegativeButton(R.string.dont, null)
                .show();
        AlertDialogUtil.changeTitleAndMessageColor(this, dialog);
    }

    /**
     * ———————————————————————读写SD卡权限———————————————————————end
     */

    /**
     * 动态权限申请回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onBackPressed() {
        if (mIsExit) {
            this.finish();
        } else {
            if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
                mDrawerLayout.closeDrawer(Gravity.START);
            } else {
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
