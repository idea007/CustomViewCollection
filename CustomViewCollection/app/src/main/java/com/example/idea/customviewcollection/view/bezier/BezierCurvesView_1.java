package com.example.idea.customviewcollection.view.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by idea on 2017/1/6.
 */

public class BezierCurvesView_1 extends View {

    private Paint mPaint;
    private int centerX, centerY;

    private PointF start, end, control1,control2;


    public BezierCurvesView_1(Context context) {
        super(context);
        init(context);
    }

    public BezierCurvesView_1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);

        start = new PointF(0,0);
        end = new PointF(0,0);
        control1 = new PointF(0,0);
        control2 = new PointF(0,0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX=w/2;
        centerY=h/2;
        // 初始化数据点和控制点的位置
        start.x = centerX-200;
        start.y = centerY;
        end.x = centerX+200;
        end.y = centerY;
        control1.x = centerX+200;
        control1.y = centerY-200;


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 根据触摸位置更新控制点，并提示重绘
        control2.x = event.getX();
        control2.y = event.getY();
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制数据点和控制点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);
        canvas.drawPoint(start.x,start.y,mPaint);
        canvas.drawPoint(end.x,end.y,mPaint);
        canvas.drawPoint(control1.x,control1.y,mPaint);
        canvas.drawPoint(control2.x,control2.y,mPaint);

        // 绘制辅助线
        mPaint.setStrokeWidth(4);
        canvas.drawLine(start.x,start.y,control1.x,control1.y,mPaint);
        canvas.drawLine(end.x,end.y,control2.x,control2.y,mPaint);
        canvas.drawLine(control1.x,control1.y,control2.x,control2.y,mPaint);

        // 绘制贝塞尔曲线
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);

        Path path = new Path();

        path.moveTo(start.x,start.y);
        //quadTo, cubicTo	分别为二次和三次贝塞尔曲线的方法
        path.cubicTo(control1.x,control1.y,control2.x,control2.y,end.x,end.y);

        canvas.drawPath(path, mPaint);
    }


}
