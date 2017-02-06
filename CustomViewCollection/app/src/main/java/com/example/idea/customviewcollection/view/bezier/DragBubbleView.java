package com.example.idea.customviewcollection.view.bezier;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.example.idea.customviewcollection.view.helper.CustomView;
import com.example.idea.mylibrary.utils.DpUtils;

/**
 * Created by idea on 2017/1/11.
 * 参考 @see <a href="http://www.jianshu.com/p/887b1ef3362d">drawPath实现QQ拖拽泡泡</a>
 */

public class DragBubbleView extends CustomView {

    private Path mPath;

    private int DEFAULT_RADIO = 300;


    private int CIRCLEY, CIRCLEX;
    private int startX = 0, startY = 0;
    //固定圆的半径
    private int ORIGIN_RADIO = 100;
    private int DRAG_RADIO = 200;

    //旋转圆相对于固定圆的旋转角度
    private double angle;
    private boolean flag;

    int dy, dx;


    private ValueAnimator valueX;
    private ValueAnimator valueY;
    private AnimatorSet animSetXY;

    //按下时在大圆区域内才能移动
    private Region region;


    public DragBubbleView(Context context) {
        this(context, null);
    }

    public DragBubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mDeafultPaint.setColor(Color.GRAY);
        mDeafultPaint.setStrokeWidth(DpUtils.dp2px(mContext, 1));
        mDeafultPaint.setAntiAlias(true);
        mDeafultPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();

        region = new Region();


        animSetXY = new AnimatorSet();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        CIRCLEX = (int) ((w) * 0.5 + 0.5);
        CIRCLEY = (int) ((h) * 0.5 + 0.5);
        startX = CIRCLEX;
        startY = CIRCLEY;
        ORIGIN_RADIO = w / 30;
        DRAG_RADIO = w / 20;


        Path circlePath = new Path();
        circlePath.addCircle(CIRCLEX, CIRCLEY, DRAG_RADIO, Path.Direction.CW);
        Region globalRegion = new Region(-w, -h, w, h);
        // ▼将 Path 添加到 Region 中
        region.setPath(circlePath, globalRegion);

    }

    private boolean isAllowMove;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://有事件先拦截再说！！

                getParent().requestDisallowInterceptTouchEvent(true);
                startX = (int) event.getX();
                startY = (int) event.getY();

                // ▼点击区域判断
                if (region.contains(startX, startY)) {
                    isAllowMove = true;
                }


                break;

            case MotionEvent.ACTION_MOVE:
                if (isAllowMove) {
                    startX = (int) event.getX();
                    startY = (int) event.getY();
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
                if (isAllowMove) {
                    valueX = ValueAnimator.ofInt(startX, CIRCLEX);
                    valueY = ValueAnimator.ofInt(startY, CIRCLEY);
                    animSetXY.playTogether(valueX, valueY);
                    valueX.setDuration(500);
                    valueY.setDuration(500);
                    valueX.setInterpolator(new OvershootInterpolator());
                    valueY.setInterpolator(new OvershootInterpolator());
                    valueX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            startX = (int) animation.getAnimatedValue();
                            invalidate();
                        }

                    });
                    valueY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            startY = (int) animation.getAnimatedValue();
                            invalidate();

                        }
                    });

                    animSetXY.start();
                }
                isAllowMove=false;
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        if (modeWidth == MeasureSpec.UNSPECIFIED || modeWidth == MeasureSpec.AT_MOST) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(DEFAULT_RADIO * 2, MeasureSpec.EXACTLY);
        }
        if (modeHeight == MeasureSpec.UNSPECIFIED || modeHeight == MeasureSpec.AT_MOST) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(DEFAULT_RADIO * 2, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDisplay(canvas);
    }


    private void drawDisplay(Canvas canvas) {
        mPath.reset();

        flag = (startY - CIRCLEY) * (startX - CIRCLEX) <= 0;
        dy = Math.abs(CIRCLEY - startY);
        dx = Math.abs(CIRCLEX - startX);
        angle = Math.atan(dy * 1.0 / dx);

        mDeafultPaint.setColor(Color.GRAY);
        mDeafultPaint.setStrokeWidth(DpUtils.dp2px(mContext, 1));
        if (flag) {
            //第一个点
            mPath.moveTo((float) (CIRCLEX - Math.sin(angle) * ORIGIN_RADIO), (float) (CIRCLEY - Math.cos(angle) * ORIGIN_RADIO));
            mPath.quadTo((float) ((startX + CIRCLEX) * 0.5), (float) ((startY + CIRCLEY) * 0.5), (float) (startX - Math.sin(angle) * DRAG_RADIO), (float) (startY - Math.cos(angle) * DRAG_RADIO));
            mPath.lineTo((float) (startX + Math.sin(angle) * DRAG_RADIO), (float) (startY + Math.cos(angle) * DRAG_RADIO));

            mPath.quadTo((float) ((startX + CIRCLEX) * 0.5), (float) ((startY + CIRCLEY) * 0.5), (float) (CIRCLEX + Math.sin(angle) * ORIGIN_RADIO), (float) (CIRCLEY + Math.cos(angle) * ORIGIN_RADIO));
            mPath.close();

        } else {
            //第一个点
            mPath.moveTo((float) (CIRCLEX - Math.sin(angle) * ORIGIN_RADIO), (float) (CIRCLEY + Math.cos(angle) * ORIGIN_RADIO));
            mPath.quadTo((float) ((startX + CIRCLEX) * 0.5), (float) ((startY + CIRCLEY) * 0.5), (float) (startX - Math.sin(angle) * DRAG_RADIO), (float) (startY + Math.cos(angle) * DRAG_RADIO));
            mPath.lineTo((float) (startX + Math.sin(angle) * DRAG_RADIO), (float) (startY - Math.cos(angle) * DRAG_RADIO));
            mPath.quadTo((float) ((startX + CIRCLEX) * 0.5), (float) ((startY + CIRCLEY) * 0.5), (float) (CIRCLEX + Math.sin(angle) * ORIGIN_RADIO), (float) (CIRCLEY - Math.cos(angle) * ORIGIN_RADIO));
            mPath.lineTo((float) (CIRCLEX - Math.sin(angle) * ORIGIN_RADIO), (float) (CIRCLEY + Math.cos(angle) * ORIGIN_RADIO));
        }


        canvas.drawPath(mPath, mDeafultPaint);
        canvas.drawCircle(CIRCLEX, CIRCLEY, ORIGIN_RADIO, mDeafultPaint);
        canvas.drawCircle(startX == 0 ? CIRCLEX : startX, startY == 0 ? CIRCLEY : startY, DRAG_RADIO, mDeafultPaint);//拖拽的圆

        mDeafultPaint.setColor(Color.RED);
        mDeafultPaint.setStrokeWidth(DpUtils.dp2px(mContext, 5));
        canvas.drawPoint((float) ((startX + CIRCLEX) * 0.5), (float) ((startY + CIRCLEY) * 0.5), mDeafultPaint);


    }


    private void drawDisplay_1(Canvas canvas) {
        mPath.reset();
        mPath.moveTo(mViewWidth / 2, mViewHeight / 2);
        mPath.quadTo(mViewWidth / 2 + 200, mViewHeight / 2 + 100, mViewWidth / 2 + 400, mViewHeight / 2);
        canvas.translate(-200, 0);
//        mPath.close();
        canvas.drawPath(mPath, mDeafultPaint);

    }
}
