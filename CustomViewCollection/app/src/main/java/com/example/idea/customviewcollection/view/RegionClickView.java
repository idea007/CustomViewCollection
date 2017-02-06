package com.example.idea.customviewcollection.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by idea on 2017/1/9.
 */

public class RegionClickView extends View {

    private int mViewWidth,mViewHeight;
    private Paint mPaint;
    private Path circlePath;

    /*Region 直接翻译的意思是 地域，区域。在此处应该是区域的意思。
    它和 Path 有些类似，但 Path 可以是不封闭图形，而 Region 总是封闭的。
    可以通过 setPath 方法将 Path 转换为 Region。
    */
    Region circleRegion;

    public RegionClickView(Context context) {
        this(context,null);
    }

    public RegionClickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth=w;
        mViewHeight=h;

        circlePath.addCircle(mViewWidth/2,mViewHeight/2,300, Path.Direction.CW);
        // ▼将剪裁边界设置为视图大小
        Region globalRegion = new Region(-w, -h, w, h);
        // ▼将 Path 添加到 Region 中
        circleRegion.setPath(circlePath, globalRegion);

    }

    private void init() {
        mPaint=new Paint();
        mPaint.setColor(Color.GRAY);
        circleRegion=new Region();
        circlePath = new Path();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();

                // ▼点击区域判断
                if (circleRegion.contains(x,y)){
                    Toast.makeText(this.getContext(),"圆被点击",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // ▼注意此处将全局变量转化为局部变量，方便 GC 回收 canvas
        Path circle = circlePath;
        // 绘制圆
        canvas.drawPath(circle,mPaint);
    }
}
