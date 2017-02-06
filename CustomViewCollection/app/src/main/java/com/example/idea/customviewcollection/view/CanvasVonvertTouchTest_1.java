package com.example.idea.customviewcollection.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.idea.customviewcollection.view.helper.CanvasAidUtils;
import com.example.idea.mylibrary.utils.LogUtils;

/**
 * Created by idea on 2017/1/9.
 */

public class CanvasVonvertTouchTest_1 extends View {
    private Paint mPaint;
    private int mViewWidth,mViewHeight;


    float down_x = -1;
    float down_y = -1;


    public CanvasVonvertTouchTest_1(Context context) {
        this(context,null);
    }

    public CanvasVonvertTouchTest_1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth=w;
        mViewHeight=h;
    }

    private void init() {
        mPaint=new Paint();
        mPaint.setColor(Color.GRAY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                down_x = event.getX();
                down_y = event.getY();
                invalidate();
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                down_x = down_y = -1;
                invalidate();
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float x = down_x;
        float y = down_y;

        drawTouchCoordinateSpace(canvas);       // 绘制触摸坐标系 灰色

        // ▼注意画布平移
        canvas.translate(mViewWidth/2, mViewHeight/2);

        drawTranslateCoordinateSpace(canvas);    // 绘制平移后的坐标系，红色

        if (x == -1 && y == -1) return;          // 如果没有就返回

        canvas.drawCircle(0,0,200,mPaint); // 在触摸位置绘制一个小圆
        LogUtils.w(this," x="+x+" y="+y);
    }

    /**
     *  绘制触摸坐标系，灰色，为了能够显示出坐标系，将坐标系位置稍微偏移了一点
     */
    private void drawTouchCoordinateSpace(Canvas canvas) {
        canvas.save();
        canvas.translate(10,10);
        CanvasAidUtils.set2DAxisLength(1000, 0, 1400, 0);
        CanvasAidUtils.setLineColor(Color.GRAY);
        CanvasAidUtils.draw2DCoordinateSpace(canvas);
        canvas.restore();
    }

    /**
     * 绘制平移后的坐标系，红色
     */
    private void drawTranslateCoordinateSpace(Canvas canvas) {
        CanvasAidUtils.set2DAxisLength(500, 500, 700, 700);
        CanvasAidUtils.setLineColor(Color.RED);
        CanvasAidUtils.draw2DCoordinateSpace(canvas);
        CanvasAidUtils.draw2DCoordinateSpace(canvas);
    }

}
