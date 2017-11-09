package com.liang.tind.mycv.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.liang.tind.mycv.R;

import java.lang.reflect.Field;

/**
 * Created by Sherlock on 2017/9/26.
 */

public class AlertDialogUtil {

    public static void changeTitleAndMessageColor(Context context, AlertDialog dialog){
        try {
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(dialog);
            Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
            Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
            mMessage.setAccessible(true);
            mTitle.setAccessible(true);
            TextView mMessageView = (TextView) mMessage.get(mAlertController);
            TextView mTitleView = (TextView) mTitle.get(mAlertController);
            mMessageView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            mTitleView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
