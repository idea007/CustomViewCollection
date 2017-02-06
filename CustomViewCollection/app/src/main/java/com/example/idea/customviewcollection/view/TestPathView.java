package com.example.idea.customviewcollection.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.idea.customviewcollection.R;
import com.example.idea.mylibrary.utils.LogUtils;

/**
 * Created by idea on 2017/1/5.
 */

public class TestPathView extends View {
    // 画笔
    private Paint mPaint;

    // View 宽高
    private int mViewWidth;
    private int mViewHeight;

    public TestPathView(Context context) {
        this(context, null);

    }

    public TestPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(15);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);


        // drawSearch_4的参数初始化
        pos = new float[2];
        tan = new float[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;       // 缩放图片
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher, options);
        mMatrix = new Matrix();

        ininAnim();

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
        Log.i("Draw", "onDraw()");
        drawSearch(canvas);
    }

    private ValueAnimator mSearchingAnimator=ValueAnimator.ofFloat(0, 1).setDuration(3000);
    private Animator.AnimatorListener mAnimatorListener;
    float value;

    private void ininAnim() {
        mSearchingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                value = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        mAnimatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                LogUtils.w(this,"onAnimationEnd");

                mSearchingAnimator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };

        mSearchingAnimator.addListener(mAnimatorListener);

        mSearchingAnimator.start();
    }


    private void drawSearch(Canvas canvas) {


        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        path_circle = new Path();
        RectF r=new RectF(100,100,200,200);
        path_circle.addArc(r,0,360f);

        mMeasure = new PathMeasure();
        mMeasure.setPath(path_circle,false);

        Path dst=new Path();
        mMeasure.getSegment(mMeasure.getLength()*value,mMeasure.getLength(),dst,true);
        canvas.drawPath(dst,mPaint);

    }


    private void drawSearch_7(Canvas canvas) {
        canvas.translate(mViewWidth / 2, mViewHeight / 2);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        path_srarch = new Path();
        path_circle = new Path();

        mMeasure = new PathMeasure();



        RectF r=new RectF(-50,-50,50,50);
        path_srarch.addArc(r,0,90);

        RectF oval2 = new RectF(-100, -100, 100, 100);      // 外部圆环
        path_circle.addArc(oval2,0 , -180f);


        mMeasure.setPath(path_circle, false);
        mMeasure.getPosTan(0, pos, tan); //获取指定长度的位置坐标及该点切线值

        path_srarch.lineTo(pos[0], pos[1]);





        canvas.drawPath(path_srarch,mPaint);
        canvas.drawPath(path_circle,mPaint);
    }


    // 放大镜与外部圆环
    private Path path_srarch;
    private Path path_circle;

    // 测量Path 并截取部分的工具
    private PathMeasure mMeasure;


    private void drawSearch_6(Canvas canvas) {
        canvas.translate(mViewWidth / 2, mViewHeight / 2);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        path_srarch = new Path();
        path_circle = new Path();

        mMeasure = new PathMeasure();

        // 注意,不要到360度,否则内部会自动优化,测量不能取到需要的数值
        RectF oval1 = new RectF(-50, -50, 50, 50);          // 放大镜圆环

        path_srarch.addArc(oval1, 45, 359.9f);

        RectF oval2 = new RectF(-100, -100, 100, 100);      // 外部圆环
        path_circle.addArc(oval2, 45, -359.9f);

        float[] pos = new float[2];

        mMeasure.setPath(path_circle, false);               // 放大镜把手的位置
        mMeasure.getPosTan(0, pos, null);

        path_srarch.lineTo(pos[0], pos[1]);                 // 放大镜把手

        canvas.drawPath(path_srarch,mPaint);
//        canvas.drawPath(path_circle,mPaint);







    }


    private void drawSearch_5(Canvas canvas) {
        canvas.translate(mViewWidth / 2, mViewHeight / 2);
        Path path = new Path();
        path.addRect(-200, -200, 200, 200, Path.Direction.CW);
        PathMeasure measure = new PathMeasure(path, false);

        currentValue += 0.005;                                  // 计算当前的位置在总长度上的比例[0,1]
        if (currentValue >= 1) {
            currentValue = 0;
        }

        // 获取当前位置的坐标以及趋势的矩阵
        measure.getMatrix(measure.getLength() * currentValue, mMatrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);

        mMatrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);   // <-- 将图片绘制中心调整到与当前点重合(注意:此处是前乘pre)

        canvas.drawPath(path, mPaint);                                   // 绘制 Path
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);                     // 绘制箭头

        invalidate();

    }


    private float currentValue = 0;     // 用于纪录当前的位置,取值范围[0,1]映射Path的整个长度
    private float[] pos;                // 当前点的实际位置
    private float[] tan;                // 当前点的tangent值,用于计算图片所需旋转的角度
    private Bitmap mBitmap;             // 箭头图片
    private Matrix mMatrix;             // 矩阵,用于对图片进行一些操作

    private void drawSearch_4(Canvas canvas) {
        canvas.translate(mViewWidth / 2, mViewHeight / 2);      // 平移坐标系
        Path path = new Path();                                 // 创建 Path

        path.addCircle(0, 0, 200, Path.Direction.CW);           // 添加一个圆形
//        path.addRect(-200, -200, 200, 200, Path.Direction.CW);
        PathMeasure measure = new PathMeasure(path, false);     // 创建 PathMeasure

        currentValue += 0.005;                                  // 计算当前的位置在总长度上的比例[0,1]
        if (currentValue >= 1) {
            currentValue = 0;
        }

        //tan 该点的正切值
        measure.getPosTan(measure.getLength() * currentValue, pos, tan);        // 获取当前位置的坐标以及趋势

        Log.i("getPosTan", "tan[0]=" + tan[0] + " tan[1]=" + tan[1]);
        mMatrix.reset();                                                        // 重置Matrix
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI); // 计算图片旋转角度

        mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);   // 旋转图片
        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);   // 将图片绘制中心调整到与当前点重合

        canvas.drawPath(path, mPaint);                                   // 绘制 Path
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);                     // 绘制箭头
/*
        页面刷新此处是在 onDraw 里面调用了 invalidate 方法来保持界面不断刷新，但并不提倡这么做，正确对做法应该是使用 线程 或者 ValueAnimator 来控制界面的刷新，关于控制页面刷新这一部分会在后续的 动画部分 详细讲解，同样敬请期待
*/
        invalidate();                                                           // 重绘页面
    }

    private void drawSearch_3(Canvas canvas) {
        canvas.translate(mViewWidth / 2, mViewHeight / 2);      // 平移坐标系

        Path path = new Path();

        path.addRect(-50, -50, 50, 50, Path.Direction.CW);  // 添加小矩形
        path.addRect(-200, -200, 200, 200, Path.Direction.CW);  // 添加大矩形

        canvas.drawPath(path, mPaint);                    // 绘制 Path

        PathMeasure measure = new PathMeasure(path, false);     // 将Path与PathMeasure关联

        float len1 = measure.getLength();                       // 获得第一条路径的长度

        measure.nextContour();                                  // 跳转到下一条路径

        float len2 = measure.getLength();                       // 获得第二条路径的长度

        Log.i("LEN", "len1=" + len1);                              // 输出两条路径的长度
        Log.i("LEN", "len2=" + len2);
    }

    private void drawSearch_2(Canvas canvas) {
        canvas.translate(mViewWidth / 2, mViewHeight / 2);          // 平移坐标系

        /*这里Path类创建矩形路径的参数与上篇canvas绘制矩形差不多，唯一不同的一点是增加了Path.Direction参数；
        Path.Direction有两个值：
        Path.Direction.CCW：是counter-clockwise缩写，指创建逆时针方向的矩形路径；
        Path.Direction.CW：是clockwise的缩写，指创建顺时针方向的矩形路径；*/

        Path path = new Path();                                     // 创建Path并添加了一个矩形
        path.addRect(-200, -200, 200, 200, Path.Direction.CW);

        Path dst = new Path();                                      // 创建用于存储截取后内容的 Path

        //从上面的示例可以看到 dst 中的线段保留了下来，可以得到结论：被截取的 Path 片段会添加到 dst 中，而不是替换 dst 中到内容。
        dst.addCircle(0, 0, 180, Path.Direction.CW);

        PathMeasure measure = new PathMeasure(path, false);         // 将 Path 与 PathMeasure 关联

        // 截取一部分存入dst中，并使用 moveTo 保持截取得到的 Path 第一个点的位置不变
        measure.getSegment(200, 600, dst, true);

//        // 截取一部分 不使用 startMoveTo, 保持 dst 的连续性
//        measure.getSegment(200, 600, dst, false);

/*
        从该示例我们又可以得到一条结论：如果 startWithMoveTo 为 true,
        则被截取出来到Path片段保持原状，如果 startWithMoveTo 为 false，
        则会将截取出来的 Path 片段的起始点移动到 dst 的最后一个点，以保证 dst 的连续性。
*/

        canvas.drawPath(dst, mPaint);                        // 绘制 dst


    }

    private void drawSearch_1(Canvas canvas) {
        canvas.translate(mViewWidth / 2, mViewHeight / 2);

        Path path = new Path();

        path.lineTo(0, 200);
        path.lineTo(200, 200);
        path.lineTo(200, 0);
       /* 1. 不论 forceClosed 设置为何种状态(true 或者 false)， 都不会影响原有Path的状态，
       即 Path 与 PathMeasure 关联之后，之前的的 Path 不会有任何改变。
        2. forceClosed 的设置状态可能会影响测量结果，如果 Path 未闭合但在与
        PathMeasure 关联的时候设置 forceClosed 为 true 时，测量结果可能会比
        Path 实际长度稍长一点，获取到到是该 Path 闭合时的状态。*/

        PathMeasure measure1 = new PathMeasure(path, false);
        PathMeasure measure2 = new PathMeasure(path, true);

        Log.e("TAG", "forceClosed=false---->" + measure1.getLength());
        Log.e("TAG", "forceClosed=true----->" + measure2.getLength());

        canvas.drawPath(path, mPaint);
    }
}
