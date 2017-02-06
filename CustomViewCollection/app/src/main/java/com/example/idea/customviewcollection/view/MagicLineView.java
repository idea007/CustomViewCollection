package com.example.idea.customviewcollection.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.idea.mylibrary.utils.DpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by idea on 2017/1/6.
 */

public class MagicLineView extends View {

    // 画笔
    private Paint mPaint;
    // View 宽高
    private int mViewWidth;
    private int mViewHeight;

    private double speedA, speedB;
    private float aX, aY, bX, bY, angleA, angleB;
    private int aXR, aYR, bXR, bYR;

    private float mDuration = 4000;                     // 变化总时长
    private float mCurrent = 0;                         // 当前已进行时长
    private float mCount = 400;                         // 将时长总共划分多少份
    private float mPiece = mDuration/mCount;            // 每一份的时长



    public MagicLineView(Context context) {
        this(context, null);
    }

    public MagicLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);
        this.mPaint.setStyle(Paint.Style.STROKE);


        speedA = 0.5;
        speedB = 0.15;
        aXR = 320;
        aYR = 320;
        bXR = 320;
        bYR = 320;


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLineCase(canvas);
    }

    private void drawLineCase(Canvas canvas) {
        canvas.translate(mViewWidth / 2, mViewHeight / 2);

        angleA += speedA;
        angleB += speedB;
        aX = (float) (Math.cos(angleA) * aXR);
        aY = (float) (Math.sin(angleA) * aYR);
        bX = (float) (Math.cos(angleB) * bXR);
        bY = (float) (Math.sin(angleB) * bYR);
        corrDatas.add(new CorrdinateData(aX, aY, bX, bY));

        for (int i = 0; i < corrDatas.size(); i++) {
            CorrdinateData cd = corrDatas.get(i);
            canvas.drawLine(cd.p1X, cd.p1Y, cd.p2X, cd.p2Y, mPaint);
        }


        mCurrent += mPiece;
        if (mCurrent < mDuration){
            postInvalidateDelayed((long) mPiece);
        }

    }


    //记录移动过的所有点的数据
    private List<CorrdinateData> corrDatas = new ArrayList<>();

    private class CorrdinateData {
        float p1X, p1Y, p2X, p2Y;

        CorrdinateData(float p1X, float p1Y, float p2X, float p2Y) {
            this.p1X = p1X;
            this.p1Y = p1Y;
            this.p2X = p2X;
            this.p2Y = p2Y;
        }
    }

}
