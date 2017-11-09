package com.liang.tind.mycv.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.liang.tind.mycv.R;
import com.liang.tind.mycv.utils.BitmapUtil;
import com.liang.tind.mycv.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sherlock on 2017/11/8.
 */

public class FlipboardIconView extends View {
    private Bitmap bitmap;
    private Camera camera = new Camera();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private ObjectAnimator animatorY;
    private ObjectAnimator animatorZ;
    private ObjectAnimator animatorFixY;
    private int degreeY;
    private int degreeZ;
    private int degreeFixY;
    private static int targetDegreeY;
    private static int durationZ;
    private static int durationY;
    public static final int DEFAULT_TARGET_DEGREE_Y = 45;
    public static final int DEFAULT_DURATION_Y = 500;
    public static final int DEFAULT_DURATION_Z = 1000;

    private List<AnimationListener> mAnimationListenerList;
    private AnimatorSet mAnimatorSet;

    public FlipboardIconView(Context context) {
        this(context, null);
    }

    public FlipboardIconView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlipboardIconView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlipboardIconView);
        targetDegreeY = typedArray.getInt(R.styleable.FlipboardIconView_target_degree_y, DEFAULT_TARGET_DEGREE_Y);
        durationY = typedArray.getInt(R.styleable.FlipboardIconView_duration_degree_y, DEFAULT_DURATION_Y);
        durationZ = typedArray.getInt(R.styleable.FlipboardIconView_duration_degree_z, DEFAULT_DURATION_Z);
        BitmapDrawable drawable = (BitmapDrawable) typedArray.getDrawable(R.styleable.FlipboardIconView_background_bitmap);

        typedArray.recycle();
        Bitmap defaultBitmap;
        if (drawable == null) {

            defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_splash);

        } else {
            defaultBitmap = drawable.getBitmap();
        }
        bitmap = BitmapUtil.fitBitmap(defaultBitmap, (int) (UIUtils.getWindowWidth((Activity) getContext()) * 0.8));
        init();
    }

    private void init() {
        camera = new Camera();
        //camera糊脸修正;
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float newZ = -displayMetrics.density * 6;
        camera.setLocation(0, 0, newZ);

        initDegreeYAnimation();
        initDegreeZAnimation();
        initDegreeFixYAnimation();

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playSequentially(animatorY, animatorZ, animatorFixY);

        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (mAnimationListenerList != null && mAnimationListenerList.size() > 0) {
                    for (AnimationListener animationListener : mAnimationListenerList) {
                        animationListener.onAnimationStart();
                    }
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mAnimationListenerList != null && mAnimationListenerList.size() > 0) {
                    for (AnimationListener animationListener : mAnimationListenerList) {
                        animationListener.onAnimationEnd();
                    }
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    private void initDegreeFixYAnimation() {
        animatorFixY = ObjectAnimator.ofInt(this, "degreeFixY", 0, targetDegreeY);
        animatorFixY.setDuration(durationY);
    }

    private void initDegreeZAnimation() {
        animatorZ = ObjectAnimator.ofInt(this, "degreeZ", 0, 270);
        animatorZ.setDuration(durationZ);
    }

    private void initDegreeYAnimation() {
        animatorY = ObjectAnimator.ofInt(this, "degreeY", 0, targetDegreeY);
        animatorY.setDuration(durationY);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = this.getMeasuredSize(widthMeasureSpec, true);
        int height = this.getMeasuredSize(heightMeasureSpec, false);
        setMeasuredDimension(width, height);
    }

    private int getMeasuredSize(int length, boolean isWidth) {
        int specMode = MeasureSpec.getMode(length);
        int specSize = MeasureSpec.getSize(length);
        int retSize;
        int padding = (isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom());

        // MATCH_PARENT or xx dp
        if (specMode == MeasureSpec.EXACTLY) {
            retSize = specSize;
        } else {
            // WRAP_CONTENT or unset
            retSize = isWidth ? bitmap.getWidth() + padding : bitmap.getHeight() + padding;
            if (specMode == MeasureSpec.UNSPECIFIED) {
                retSize = Math.min(retSize, specSize);
            }
        }

        return retSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;

        //绘制动的半边.
        //开始先绕Y轴旋转 targetDegreeY 度
        //然后是中间波浪效果：把裁剪出来的那半边canvas旋转了targetDegreeY的那半边绕Z轴旋转270°,
        //这样因为那半边canvas是已经旋转了targetDegreeY的，所以中间波浪动画效果就实现了这半边扫过的部分变得凸起
        //最后再绕X轴旋转targetDegreeX 度

        canvas.save();
        camera.save();

        canvas.translate(centerX, centerY);
        camera.rotateY(-degreeY);
        canvas.rotate(-degreeZ);
        camera.applyToCanvas(canvas);
        canvas.clipRect(0, -centerY, centerX, centerY);
        canvas.rotate(degreeZ);
        canvas.translate(-centerX, -centerY);
        canvas.drawBitmap(bitmap, x, y, paint);

        canvas.restore();
        camera.restore();
        //绘制不动的那半边
        canvas.save();
        camera.save();

        canvas.translate(centerX, centerY);
        canvas.rotate(-degreeZ);
        camera.rotateY(degreeFixY);
        camera.applyToCanvas(canvas);
        canvas.clipRect(-centerX, -centerY, 0, centerY);
        canvas.rotate(degreeZ);
        canvas.translate(-centerX, -centerY);
        canvas.drawBitmap(bitmap, x, y, paint);

        canvas.restore();
        camera.restore();

    }

    public static interface AnimationListener {
        void onAnimationEnd();

        void onAnimationStart();
    }

    public void addAnimationListener(AnimationListener animationListener) {
        if (mAnimationListenerList == null) {
            mAnimationListenerList = new ArrayList<>();
        }
        mAnimationListenerList.add(animationListener);
    }

    public boolean removeAnimationListener(AnimationListener animationListener) {
        boolean result = false;
        if (mAnimationListenerList != null) {
            result = mAnimationListenerList.remove(animationListener);
        }
        return result;
    }

    public void setDegreeY(int degreeY) {
        this.degreeY = degreeY;
        invalidate();
    }

    public void setDegreeZ(int degreeZ) {
        this.degreeZ = degreeZ;
        invalidate();
    }

    public void setDegreeFixY(int degreeFixY) {
        this.degreeFixY = degreeFixY;
        invalidate();
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        invalidate();
    }

    public void reStart() {
        degreeY = 0;
        degreeZ = 0;
        degreeFixY = 0;
        mAnimatorSet.start();
    }

    public void pause() {
        mAnimatorSet.pause();
    }

    public void start() {
        mAnimatorSet.start();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mAnimatorSet.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAnimatorSet.end();
    }
}
