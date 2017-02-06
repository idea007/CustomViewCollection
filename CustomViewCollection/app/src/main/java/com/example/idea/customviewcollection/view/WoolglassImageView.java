package com.example.idea.customviewcollection.view;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by idea on 2017/1/22.
 */

public class WoolglassImageView extends ImageView {
    public WoolglassImageView(Context context) {
        super(context);
        setScaleType(ScaleType.MATRIX);
    }

    public WoolglassImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.MATRIX);
    }

    public WoolglassImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setScaleType(ScaleType.MATRIX);
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        if (getDrawable() == null) {
            return super.setFrame(l, t, r, b);
        }
        Matrix matrix = getImageMatrix();
        float scaleFactor = getWidth() / (float) getDrawable().getIntrinsicWidth();
        matrix.setScale(scaleFactor, scaleFactor, 0, 0);
        setImageMatrix(matrix);
        return super.setFrame(l, t, r, b);
    }
}
