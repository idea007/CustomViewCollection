package com.example.idea.customviewcollection.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.example.idea.mylibrary.utils.LogUtils;

/**
 * Created by idea on 2017/1/6.
 * 参考 @see <a href="https://github.com/gelitenight/WaveView">github上开源WaveView</a>
 *
 * 参考 @see <a href="http://biandroid.iteye.com/blog/1399462">Matrix</a>
 */

public class WaveView extends View {
    private int mViewWidth,mViewHeight;

    /**后面wave 颜色*/
    public static final int behind_wave_color = Color.parseColor("#50AAAAFF");
    /**前面wave 颜色*/
    public static final int front_wave_color = Color.parseColor("#70AAAAFF");

    // shader containing repeated waves
    private BitmapShader mWaveShader;

    // shader matrix
    private Matrix mShaderMatrix;

    // paint to draw wave
    private Paint mViewPaint;
    // paint to draw border
    private Paint mBorderPaint;


    //振幅
    private float mAmplitudeRatio = 0.05f;

    //波长
    private float mWaveLengthRatio = 1.0f;

    //水位高低 可用来设置进度
    private float mWaterLevelRatio = 0.5f;

    //波左右平移
    private float mWaveShiftRatio = 0.0f;


    //频率
    private double mDefaultAngularFrequency;

    public WaveView(Context context) {
        this(context,null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mShaderMatrix = new Matrix();
        mViewPaint = new Paint();
        mViewPaint.setAntiAlias(true);


        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(5);
        mBorderPaint.setColor(Color.WHITE);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth=w;
        mViewHeight=h;

        createShader();
    }

    private void createShader() {
        mDefaultAngularFrequency = 2.0f * Math.PI / 1.0f / mViewWidth;
        Bitmap bitmap = Bitmap.createBitmap(mViewWidth, mViewHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint wavePaint = new Paint();
        wavePaint.setStrokeWidth(2);
        wavePaint.setAntiAlias(true);

        final int endX = mViewWidth + 1;
        final int endY = mViewHeight + 1;

        float[] waveY = new float[endX];

        wavePaint.setColor(behind_wave_color);

        for (int beginX = 0; beginX < endX; beginX++) {
            double wx = beginX * mDefaultAngularFrequency*2;
            float beginY = (float) (mViewHeight*0.5f + mViewHeight*0.05f * Math.sin(wx));
            canvas.drawLine(beginX, beginY, beginX, endY, wavePaint);

            waveY[beginX] = beginY;
        }

        wavePaint.setColor(front_wave_color);
        final int wave2Shift = (int) (mViewWidth / 4);
        for (int beginX = 0; beginX < endX; beginX++) {
            canvas.drawLine(beginX, waveY[(beginX + wave2Shift) % endX], beginX, endY, wavePaint);
        }

        // use the bitamp to create the shader
        //        1.CLAMP 拉伸 拉伸的是图片最后的哪一个像素，不断重复
        //        2.REPEAT 重复 横向、纵向不断重复
        //        3.MIRROR 镜像 横向不断翻转重复，纵向不断翻转重复
        mWaveShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        mViewPaint.setShader(mWaveShader);



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawDispay_1(canvas);

    }

    private void drawDispay(Canvas canvas) {
        canvas.drawCircle(mViewWidth/2,mViewHeight/2,mViewWidth/2,mViewPaint);
    }

    private void drawDispay_2(Canvas canvas) {

        mViewPaint.setColor(behind_wave_color);
        for (int beginX = 0; beginX < mViewWidth; beginX++) {
            double wx = beginX * mDefaultAngularFrequency;
            float beginY = (float) (mViewHeight*0.5f + mViewHeight*0.05f * Math.sin(wx));
            canvas.drawLine(beginX, beginY, beginX, mViewHeight, mViewPaint);
            LogUtils.w(this,"startx="+beginX+" starty="+beginY+" stopx="+beginX+" stopY="+mViewHeight);
        }

        mViewPaint.setColor(front_wave_color);


    }

    private void drawDispay_1(Canvas canvas) {
        canvas.drawCircle(mViewWidth/2,mViewHeight/2,mViewWidth/2,mViewPaint);

        // modify paint shader according to mShowWave state
        if (mWaveShader != null) {
            // first call after mShowWave, assign it to our paint
            if (mViewPaint.getShader() == null) {
                mViewPaint.setShader(mWaveShader);
            }

            // sacle shader according to mWaveLengthRatio and mAmplitudeRatio
            // this decides the size(mWaveLengthRatio for width, mAmplitudeRatio for height) of waves
            mShaderMatrix.setScale(
                    mWaveLengthRatio / 1.0f,
                    mAmplitudeRatio / 0.05f,
                    0,
                    mViewHeight*0.5f);
            // translate shader according to mWaveShiftRatio and mWaterLevelRatio
            // this decides the start position(mWaveShiftRatio for x, mWaterLevelRatio for y) of waves
            mShaderMatrix.postTranslate(
                    mWaveShiftRatio * mViewWidth,
                    (0.5f - mWaterLevelRatio) * mViewHeight);

            // assign matrix to invalidate the shader
            mWaveShader.setLocalMatrix(mShaderMatrix);

//            float borderWidth = mBorderPaint == null ? 0f : mBorderPaint.getStrokeWidth();
//                    if (borderWidth > 0) {
//                        canvas.drawCircle(mViewWidth / 2f, mViewHeight / 2f,
//                                (mViewWidth - borderWidth) / 2f - 1f, mBorderPaint);
//                    }
//                    float radius = mViewWidth / 2f - borderWidth;
//                    canvas.drawCircle(mViewWidth / 2f, mViewHeight / 2f, radius, mViewPaint);

        } else {
            mViewPaint.setShader(null);
        }
    }
}
