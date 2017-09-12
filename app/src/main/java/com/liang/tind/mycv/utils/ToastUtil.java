package com.liang.tind.mycv.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Sherlock on 2017/8/31.
 *
 */

public class ToastUtil {

    public static void showToast(Context context,String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context,String s,int length){
        Toast.makeText(context,s,length).show();
    }
}
