package com.example.idea.customviewcollection.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.idea.customviewcollection.view.helper.CanvasAidUtils;
import com.example.idea.mylibrary.utils.LogUtils;

/**
 * Created by idea on 2017/1/9.
 */

public class CanvasVonvertTouchTest extends View {
    private Paint mPaint;
    private int mViewWidth,mViewHeight;


    float down_x = -1;
    float down_y = -1;


    public CanvasVonvertTouchTest(Context context) {
        this(context,null);
    }

    public CanvasVonvertTouchTest(Context context, AttributeSet attrs) {
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
                // ▼ 注意此处使用 getRawX，而不是 getX
                down_x = event.getRawX();
                down_y = event.getRawY();
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

    /**
     * 原理嘛，其实非常简单，我们在画布上正常的绘制，需要将画布坐标系转换为
     * 全局坐标系后才能真正的绘制内容。所以我们反着来，将获得到的全局坐标系
     * 坐标使用当前画布的逆矩阵转化一下，就转化为当前画布的坐标系坐标了。
     * */

    @Override
    protected void onDraw(Canvas canvas) {
        float[] pts = {down_x, down_y};

        drawTouchCoordinateSpace(canvas);            // 绘制触摸坐标系，灰色
        // ▼注意画布平移
        canvas.translate(mViewWidth/2, mViewHeight/2);

        drawTranslateCoordinateSpace(canvas);        // 绘制平移后的坐标系，红色

        if (pts[0] == -1 && pts[1] == -1) return;    // 如果没有就返回

        // ▼ 获得当前矩阵的逆矩阵
        Matrix invertMatrix = new Matrix();
        canvas.getMatrix().invert(invertMatrix);

        // ▼ 使用 mapPoints 将触摸位置转换为画布坐标
        invertMatrix.mapPoints(pts);

        // 在触摸位置绘制一个小圆
        canvas.drawCircle(pts[0],pts[1],200,mPaint);
        LogUtils.w(this," x="+pts[0]+" y="+pts[1]);
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
