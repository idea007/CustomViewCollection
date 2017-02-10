package com.example.idea.customviewcollection.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by idea on 2017/2/10.
 */

public class SquareMotionView extends View {

    // 画笔
    private Paint mPaint;
    private float sweepAngle = 90;

    // View 宽高
    private int mViewWidth;
    private int mViewHeight;

    public SquareMotionView(Context context) {
        this(context,null);
    }

    public SquareMotionView(Context context, AttributeSet attrs) {
        this(context,null,0);
    }

    public SquareMotionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mPaint=new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        this.mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth=w;
        mViewHeight=h;
    }


    private int mDuration = 7200;                     // 变化总时长
    private int mCurrent = 0;                         // 当前已进行时长
    private int mCount = 720;                         // 将时长总共划分多少份
    private int mPiece = mDuration/mCount;            // 每一份的时长


    int animatedValue;
    RectF rectF = new RectF();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSquareMotion(canvas);
    }

    private void drawSquareMotion(Canvas canvas) {
        animatedValue=mCurrent%3600;

        for (int r = 10; r < mViewWidth/2; r += 30) {
            canvas.rotate(animatedValue/10, mViewWidth/2, mViewHeight/2);
            mPaint.setStrokeWidth(r / 20);
            rectF.top = mViewWidth/2 - r;
            rectF.left = mViewHeight/2 - r;
            rectF.right = mViewWidth/2 + r;
            rectF.bottom = mViewHeight/2 + r;
            canvas.drawArc(rectF, 0, sweepAngle, false, mPaint);
        }

        mCurrent += mPiece;
        if (mCurrent < mDuration){
            postInvalidateDelayed((long) mPiece);
        }
    }
}
