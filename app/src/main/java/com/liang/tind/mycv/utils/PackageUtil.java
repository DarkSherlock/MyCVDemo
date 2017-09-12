package com.liang.tind.mycv.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.List;

/**
 * Created by Sherlock on 2017/9/1.
 */

public class PackageUtil {
    public static final String WE_CHAT_PACKAGE_NAME ="com.tencent.mm";
    public static final String WE_CHAT_LAUNCHER ="com.tencent.mm.ui.LauncherUI";
    public static final String QQ_PACKAGE_NAME ="com.tencent.mobileqq";
    public static final String QQ_LAUNCHER ="com.tencent.mobileqq.activity.HomeActivity";
   public static final  String QQ_URL="mqqwpa://im/chat?chat_type=wpa&uin=627669608";
    /**
     * 通过包名判断某应用是否安装在手机上
     * @param context
     * @param packageName 应用的包名
     * @return ture if app avilible,false otherwise;
     */
    public static boolean isAppAvilible(Context context,String packageName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Intent getOtherAppIntent(String packageName,String activityName){
        Intent intent = new Intent();
        ComponentName cmp=new ComponentName(packageName,activityName);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(cmp);
        return intent;
    }

    public static Intent getQQIntent(){
        return new Intent(Intent.ACTION_VIEW, Uri.parse(QQ_URL));
    }
}
