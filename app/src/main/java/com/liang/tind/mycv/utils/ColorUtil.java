package com.liang.tind.mycv.utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;


/**
 * Created by Sherlock on 2017/8/31.
 */

public class ColorUtil {
    public static int getColor(Context context, @ColorRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(id, context.getTheme());
        } else {
            return context.getResources().getColor(id);
        }

    }

}
