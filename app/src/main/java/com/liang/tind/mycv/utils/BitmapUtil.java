package com.liang.tind.mycv.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by Sherlock on 2017/11/9.
 */

public class BitmapUtil {

    /**
     * fuction: 设置固定的宽度，高度随之变化，使图片不会变形
     *
     * @param target
     * 需要转化bitmap参数
     * @param newWidth
     * 设置新的宽度
     * @return
     */
    public static Bitmap fitBitmap(Bitmap target, int newWidth)
    {
        int width = target.getWidth();
        int height = target.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) newWidth) / width;
        // float scaleHeight = ((float)newHeight) / height;
        int newHeight = (int) (scaleWidth * height);
        matrix.postScale(scaleWidth, scaleWidth);
        // Bitmap result = Bitmap.createBitmap(target,0,0,width,height,
        // matrix,true);
        Bitmap bmp = Bitmap.createBitmap(target, 0, 0, width, height, matrix,
                true);
        if (target != null && !target.equals(bmp) && !target.isRecycled()){
            target.recycle();
            target = null;
        }
        return bmp;// Bitmap.createBitmap(target, 0, 0, width, height, matrix,
        // true);
    }

}
