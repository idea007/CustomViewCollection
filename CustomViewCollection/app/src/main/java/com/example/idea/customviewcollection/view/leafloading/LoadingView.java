package com.example.idea.customviewcollection.view.leafloading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.idea.mylibrary.utils.LogUtils;

/**
 * Created by idea on 2017/1/10.
 */

public class LoadingView extends View {

    private Paint mPaint;
    private int mViewWidth;
    private int mViewHeight;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);       //设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        mPaint.setStrokeWidth(10f);         //设置画笔宽度为10px
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        LogUtils.w(this, "mViewWidth=" + mViewWidth + " mViewHeight=" + mViewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        displyDraw(canvas);

    }



    private float currentRatio; //当前比例
    private int mCurrentProgressPosition;

    private int mArcRadius;

    private void displyDraw(Canvas canvas) {
        mArcRadius=100;
        currentRatio += 0.005;                                  // 计算当前的位置在总长度上的比例[0,1]
        if (currentRatio >= 1) {
            currentRatio = 0;
        }


        // mProgressWidth为进度条的宽度，根据当前进度算出进度条的位置
        mCurrentProgressPosition = (int)(currentRatio*800);
        RectF rectF = new RectF(0, 0, mArcRadius * 2, mArcRadius * 2);
        RectF mWhiteRectF = new RectF(mArcRadius, 0, 800, mArcRadius * 2);
        RectF mOrangeRectF =new RectF(mArcRadius, 0, 800, mArcRadius * 2);


        if(mCurrentProgressPosition < mArcRadius) {
            LogUtils.w(this, "mCurrentProgressPosition = " + mCurrentProgressPosition);

            mPaint.setColor(Color.GRAY);
            // 1.绘制白色ARC
            canvas.drawArc(rectF, 90, 180, false, mPaint);
            mWhiteRectF.left = mArcRadius;
            canvas.drawRect(mWhiteRectF, mPaint);

            // 3.绘制棕色 ARC
            // 单边角度
            int angle = (int) Math.toDegrees(Math.acos((mArcRadius - mCurrentProgressPosition)
                    / (float) mArcRadius));
            // 起始的位置
            int startAngle = 180 - angle;
            // 扫过的角度
            int sweepAngle = 2 * angle;
            LogUtils.w(this, "startAngle = " + startAngle);
            mPaint.setColor(Color.YELLOW);
            canvas.drawArc(rectF, startAngle, sweepAngle, false, mPaint);
        }else{
            mPaint.setColor(Color.GRAY);
            // 1.绘制white RECT
            mWhiteRectF.left = mCurrentProgressPosition;
            canvas.drawRect(mWhiteRectF, mPaint);

            mPaint.setColor(Color.YELLOW);
            // 2.绘制Orange ARC
            canvas.drawArc(rectF, 90, 180, false, mPaint);
            // 3.绘制orange RECT
            mOrangeRectF.left = mArcRadius;
            mOrangeRectF.right = mCurrentProgressPosition;
            canvas.drawRect(mOrangeRectF, mPaint);

        }


        invalidate();

    }


    private void displyDraw_1(Canvas canvas) {
        canvas.translate(mViewWidth / 2, mViewHeight / 2);
        RectF rectF = new RectF(100,100,400,400);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF,mPaint);

        // 绘制圆弧
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF,90,180,true,mPaint);

    }
}