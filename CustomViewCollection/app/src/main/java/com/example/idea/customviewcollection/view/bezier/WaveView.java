package com.example.idea.customviewcollection.view.bezier;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.idea.customviewcollection.R;
import com.example.idea.mylibrary.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by idea on 2017/1/7.
 *
 * @see <a href="https://github.com/crazyandcoder/WaveView">波浪动画进度条</a>
 *
 *
 */

public class WaveView extends View {


    private Paint mPaint;
    private float mViewWidth, mViewHeight;

    //波浪颜色
    private int waveColor;
    //水位线高度
    private int mLevelLine;

    //进度 0~100
    private int progress =50;

    //振幅
    private float mAmplitudeRatio = 0.1f;

    //波长
    private float mWaveLengthRatio = 1.0f;

    /**
     * 后面wave 颜色
     */
    public static final int behind_wave_color = Color.parseColor("#50AAAAFF");
    /**
     * 前面wave 颜色
     */
    public static final int front_wave_color = Color.parseColor("#70AAAAFF");

    /**
     * 二阶贝塞尔曲线的控制点列表
     */
    private List<Point> mPointsList;

    /**
     * 波纹路径
     */
    private Path mWavePath;

    /**
     * 被隐藏的最左边的波形
     */
    private float mLeftSide;

    /**
     * 移动的距离
     */
    private float mMoveLen;

    /**
     * 水波平移速度
     */
    public static final float SPEED = 5f;


    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);


    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WaveView, defStyleAttr, 0);
        waveColor = a.getColor(R.styleable.WaveView_wave_wavecolor, Color.BLUE);
        a.recycle();
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;

        createPath();
    }

    private void createPath() {
        //水位线从最下面开始
        mLevelLine = (int) mViewHeight;
        //当前水位线
        mLevelLine = mLevelLine * progress / 100;
        if (mLevelLine < 0) {
            mLevelLine = 0;
        }

        mLeftSide = -mViewWidth/mWaveLengthRatio;

        // 这里计算在可见的View宽度中能容纳几个波形，注意n上取整
        int n = (int) Math.round(1.0f/mWaveLengthRatio+0.5);

        // n个波形需要4n+1个点，但是我们要预留一个波形在左边隐藏区域，所以需要4n+5个点
        for (int i = 0; i < (4 * n + 5); i++) {
            // 从P0开始初始化到P4n+4，总共4n+5个点
            float x = (i * (mViewWidth/mWaveLengthRatio)) / 4 - (mViewWidth/mWaveLengthRatio);
            float y = 0;
            switch (i % 4) {
                case 0:
                case 2:
                    // 零点位于水位线上
                    y = mLevelLine;
                    break;
                case 1:
                    // 往下波动的控制点
                    y = mLevelLine + mViewHeight*mAmplitudeRatio;
                    break;
                case 3:
                    // 往上波动的控制点
                    y = mLevelLine - mViewHeight*mAmplitudeRatio;
                    break;
            }
            mPointsList.add(new Point(x, y));
            LogUtils.w(this,"point x="+x+" y="+y);


        }

    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(front_wave_color);


        mPointsList = new ArrayList<Point>();
        mWavePath = new Path();

    }

    public int getProgress() {
        return progress;
    }

    /**
     * 设置进度
     **/
    public void setProgress(int chargePercent) {
        if (chargePercent < 0 || chargePercent > 100) {
            progress = 100;
        } else {
            // 水位百分比,progress越大水位线越低
            progress = 100 - (chargePercent);
        }
        invalidate();
    }

    /**
     * 所有点的x坐标都还原到初始状态，也就是一个周期前的状态
     */
    private void resetPoints() {
        mLeftSide = -mViewWidth/mWaveLengthRatio;
        for (int i = 0; i < mPointsList.size(); i++) {
            mPointsList.get(i).setX(i * (mViewWidth/mWaveLengthRatio) / 4 - mViewWidth/mWaveLengthRatio);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mWavePath.reset();
        int i = 0;
        mWavePath.moveTo(mPointsList.get(0).getX(), mPointsList.get(0).getY());
        for (; i < mPointsList.size() - 2; i = i + 2) {
            mWavePath.quadTo(mPointsList.get(i + 1).getX(), mPointsList.get(i + 1).getY(),
                    mPointsList.get(i + 2).getX(), mPointsList.get(i + 2).getY());
        }
        mWavePath.lineTo(mPointsList.get(i).getX(), mViewHeight);
        mWavePath.lineTo(mLeftSide, mViewHeight);

//        mWavePath.addArc(new RectF(0,0,mViewWidth,mViewHeight),0,180);
        mWavePath.close();

        // mPaint的Style是FILL，会填充整个Path区域
        canvas.drawPath(mWavePath, mPaint);



        update();




    }

    private void update() {
        // 记录平移总位移
        mMoveLen += SPEED;

        mLeftSide += SPEED;
        // 波形平移
        for (int j = 0; j < mPointsList.size(); j++) {
            mPointsList.get(j).setX(mPointsList.get(j).getX() + SPEED);
            switch (j % 4) {
                case 0:
                case 2:
                    mPointsList.get(j).setY(mLevelLine);
                    break;
                case 1:
                    mPointsList.get(j).setY(mLevelLine + mViewHeight*mAmplitudeRatio);
                    break;
                case 3:
                    mPointsList.get(j).setY(mLevelLine - mViewHeight*mAmplitudeRatio);
                    break;
            }
        }
        if (mMoveLen >= (mViewWidth/mWaveLengthRatio)) {
            // 波形平移超过一个完整波形后复位
            mMoveLen = 0;
            resetPoints();
        }

        invalidate();
    }


    class Point {
        private float x;

        private float y;

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

    }
}
