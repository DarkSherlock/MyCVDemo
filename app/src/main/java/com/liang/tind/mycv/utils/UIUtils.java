package com.liang.tind.mycv.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class UIUtils {
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);

	}

	public static int getWindowHeight(Activity activity) {
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

		return mDisplayMetrics.heightPixels;
	}

	public static int getWindowWidth(Activity activity) {
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

		return mDisplayMetrics.widthPixels;
	}

	public static int getStatusBarHeight(Context context) {
		int resourceId = context.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		return context.getResources().getDimensionPixelSize(resourceId);
	}

	public static FrameLayout.LayoutParams getParams(Context context) {
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display sDefaultDisplay = windowManager.getDefaultDisplay();

		return new FrameLayout.LayoutParams((int) (sDefaultDisplay.getWidth() * 0.80f),
				ViewGroup.LayoutParams.WRAP_CONTENT);

	}
}
