package com.example.idea.customviewcollection.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.example.idea.customviewcollection.R;
import com.example.idea.mylibrary.utils.DpUtils;
import com.example.idea.mylibrary.utils.LogUtils;


/**
 * Created by idea on 2017/1/1.
 */

public abstract class DrawerArrowDrawable extends Drawable {
    private static final float ARROW_HEAD_ANGLE = (float) Math.toRadians(45.0D);
    /**
     * 线间距
     */
    protected float mBarGap;
    protected float mBarSize;
    /**
     * 线厚度
     */
    protected float mBarThickness;

    protected float mMiddleArrowSize;
    protected final Paint mPaint = new Paint();
    protected  Path mPath = new Path();
    protected float mProgress;
    protected int mSize;
    protected float mVerticalMirror = 1f;
    protected float mTopBottomArrowSize;
    protected Context context;

    public DrawerArrowDrawable(Context context) {
        this.context = context;
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(context.getResources().getColor(R.color.colorPrimary));


        this.mSize = context.getResources().getDimensionPixelSize(R.dimen.ldrawer_drawableSize);
        this.mBarSize = context.getResources().getDimensionPixelSize(R.dimen.ldrawer_barSize);
        this.mTopBottomArrowSize = context.getResources().getDimensionPixelSize(R.dimen.ldrawer_topBottomBarArrowSize);
        this.mBarThickness = context.getResources().getDimensionPixelSize(R.dimen.ldrawer_thickness);
        this.mBarGap = context.getResources().getDimensionPixelSize(R.dimen.ldrawer_gapBetweenBars);
        this.mMiddleArrowSize = context.getResources().getDimensionPixelSize(R.dimen.ldrawer_middleBarArrowSize);
        this.mPaint.setStyle(Paint.Style.STROKE);

        /**
         * Paint.setStrokeJoin(Join join)设置结合处的样子，Miter:结合处为锐角， Round:结合处为圆弧：BEVEL：结合处为直线。
         */
        this.mPaint.setStrokeJoin(Paint.Join.BEVEL);
        /**
         * @see <a href="http://www.blogjava.net/java-hl/articles/393678.html">Android Paint和Color类</a>
         * 圆形样式Cap.ROUND,或方形样式Cap.SQUARE
         */
        this.mPaint.setStrokeCap(Paint.Cap.SQUARE);
        this.mPaint.setStrokeWidth(this.mBarThickness*2);
    }

    protected float lerp(float paramFloat1, float paramFloat2, float paramFloat3) {
        return paramFloat1 + paramFloat3 * (paramFloat2 - paramFloat1);
    }


    public void draw(Canvas canvas) {
        drawArrow_1(canvas);
    }

    public void drawArrow(Canvas canvas){
        Rect localRect = getBounds();
        canvas.drawRect(localRect,mPaint);





        float f1 = lerp(this.mBarSize, this.mTopBottomArrowSize, this.mProgress);
        float f2 = lerp(this.mBarSize, this.mMiddleArrowSize, this.mProgress);

        this.mPath=new Path();
        this.mPath.moveTo(DpUtils.dp2px(context.getResources(),10),DpUtils.dp2px(context.getResources(),25));
        //中间线
        this.mPath.rLineTo(DpUtils.dp2px(context.getResources(),30),0);





        canvas.save();
        canvas.rotate(mProgress*180,localRect.centerX(),localRect.centerY());
        canvas.drawPath(mPath,mPaint);
        canvas.restore();

        this.mPath=new Path();
        //上面线
//        this.mPath.moveTo(DpUtils.dp2px(context.getResources(),10)+DpUtils.dp2px(context.getResources(),15)*mProgress,DpUtils.dp2px(context.getResources(),15));
//        this.mPath.rLineTo((DpUtils.dp2px(context.getResources(),30)*8-DpUtils.dp2px(context.getResources(),30)*2*mProgress)/10,0);
        this.mPath.moveTo(DpUtils.dp2px(context.getResources(),10),DpUtils.dp2px(context.getResources(),15));
        this.mPath.rLineTo((DpUtils.dp2px(context.getResources(),30)*10)/10,0);
        canvas.save();
        canvas.rotate(mProgress*180*3/4,localRect.centerX(),localRect.centerY());
        canvas.drawPath(mPath,mPaint);
        canvas.restore();


    }

    public  void drawArrow_1(Canvas canvas){
        Rect localRect = getBounds();
//        canvas.drawRect(localRect,mPaint);
        float f1 = lerp(this.mBarSize, this.mTopBottomArrowSize, this.mProgress);
        float f2 = lerp(this.mBarSize, this.mMiddleArrowSize, this.mProgress);
        float f3 = lerp(0.0F, this.mBarThickness / 2.0F, this.mProgress);
        float f4 = lerp(0.0F, ARROW_HEAD_ANGLE, this.mProgress);
        float f5 = 0.0F;
        float f6 = 180.0F;
        float f7 = lerp(f5, f6, this.mProgress);
        float f8 = lerp(this.mBarGap + this.mBarThickness, 0.0F, this.mProgress);

        this.mPath.rewind();

        float f9 = -f2 / 2.0F;

        this.mPath.moveTo(f9 + f3, 0.0F);
        LogUtils.w("DrawerArrowDrawable","起点1 x="+(f9+f3)+" y="+0.0f);
        this.mPath.rLineTo(f2 - f3, 0.0F); //中
        LogUtils.w("DrawerArrowDrawable","终点1 x="+(f2 - f3)+" y="+0.0f);

        float f10 = (float) Math.round(f1 * Math.cos(f4));
        float f11 = (float) Math.round(f1 * Math.sin(f4));
        this.mPath.moveTo(f9, f8);
        LogUtils.w("DrawerArrowDrawable","起点2 x="+(f9)+" y="+f8);
        this.mPath.rLineTo(f10, f11); //上
        LogUtils.w("DrawerArrowDrawable","终点2 x="+(f10)+" y="+f11);

        this.mPath.moveTo(f9, -f8);
        LogUtils.w("DrawerArrowDrawable","起点3 x="+(f9)+" y="+(-f8));
        this.mPath.rLineTo(f10, -f11);  //下
        LogUtils.w("DrawerArrowDrawable","终点3 x="+(f10)+" y="+(-f11));

        this.mPath.moveTo(0.0F, 0.0F);
        this.mPath.close();
        canvas.save();
        if (!isLayoutRtl())
            canvas.rotate(180.0F, localRect.centerX(), localRect.centerY());
        canvas.rotate(f7 * mVerticalMirror, localRect.centerX(), localRect.centerY());
        canvas.translate(localRect.centerX(), localRect.centerY());
        canvas.drawPath(this.mPath, this.mPaint);
        canvas.restore();
    }


    public int getIntrinsicHeight() {
        return this.mSize;
    }

    public int getIntrinsicWidth() {
        return this.mSize;
    }

    public void setAlpha(int alpha) {
        this.mPaint.setAlpha(alpha);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public abstract boolean isLayoutRtl();

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    public void setVerticalMirror(boolean mVerticalMirror) {
        this.mVerticalMirror = mVerticalMirror ? 1 : -1;
    }

    public void setProgress(float paramFloat) {
        this.mProgress = paramFloat;

        /**
         * 当Drawable内部或者其对象调用invalidateSelf()的时候，便以Drawable对象自身为参数，
         * 让类ImageView来调用实现了Drawable.Callback的invalidateDrawable(Drawable who)方法。
         */
        invalidateSelf();
    }

    public void setColor(int resourceId) {
        this.mPaint.setColor(context.getResources().getColor(resourceId));
    }
}