package com.example.idea.mylibrary.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by idea on 2015/9/28.
 */
public class DpUtils {
    public static float dp2px(Resources resources, float dp)
    {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp)
    {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
