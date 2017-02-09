package com.example.idea.customviewcollection.view;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.idea.mylibrary.utils.LogUtils;

import java.util.ArrayList;

/**
 * Created by idea on 2017/2/8.
 */

public class AnimTextView extends TextView {

    private float mW;//单列的宽
    private int mLayoutH;//StaticLayout高
    private float mSpacingmult = 1.0f;//StaticLayout行间距的倍数1.0为正常值

    private ArrayList<String> mStrList = new ArrayList<String>();//生成的String集合
    private ArrayList<Scroller> mScrList = new ArrayList<Scroller>();//滚动类集合
    private ArrayList<StaticLayout> mLayoutList = new ArrayList<StaticLayout>();//绘制String的Layout集合


    public AnimTextView(Context context) {
        super(context);
    }

    public AnimTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 开启新数据替换老数据的动画
     * @param oldNum 老数据
     * @param newNum 新数据
     */
    public void setText(String oldNum, String newNum) {
        super.setText(newNum);
        if (TextUtils.isEmpty(oldNum) || TextUtils.isEmpty(newNum)) {
            return;
        }
        clearList();
        makeUnequalData(newNum, oldNum);
    }

    private void makeUnequalData(String newNum, String oldNum) {
        StringBuilder sb = new StringBuilder();
        int l1 = oldNum.length()-1;
        int l2 = newNum.length()-1;
        for (; l1>-1 ||l2 > -1; --l1,--l2) {
            sb.setLength(0);
            mStrList.add(0,sb.append(l1>-1?oldNum.charAt(l1):'0').append("\n").append(l2>-1?newNum.charAt(l2):'0').toString());

        }
    }

    private void clearList(){
        mStrList.clear();
        mScrList.clear();
        mLayoutList.clear();
    }

    private String mLast = null;
    @Override
    protected void onDraw(Canvas canvas) {
        LogUtils.w(this,"onDraw()方法执行");
        CharSequence str = getText();
        if (str == null)return;
        if (str != mLast){
            mLast = str.toString();
            LogUtils.w(this,"startAnim()方法执行");
            startAnim();
            postInvalidate();
            return;
        }

        if (mStrList.size() == 0 ||mScrList.size() == 0 || mLayoutList.size() == 0){
            super.onDraw(canvas);
            return;
        }

        try {
            boolean invalidate = false;
            for (int i = 0; i < mStrList.size(); i++) {

                if(i==mStrList.size()-1) {
                    canvas.save();
                    canvas.translate(i * 3 * mW / 4, 0);
                    Scroller scroller = mScrList.get(i);
                    if (scroller != null && scroller.computeScrollOffset()) {
                        canvas.translate(0, scroller.getCurrY());
                        LogUtils.w(this,"scroller.getCurrY()="+scroller.getCurrY());
                        LogUtils.w(this,"*** mStrList.get(i)"+mStrList.get(i));

                        invalidate = true;
                    }else{
                        canvas.translate(0, -mLayoutH/2+10);
//                        invalidate = true;
                    }
                    StaticLayout layout = mLayoutList.get(i);
                    if (layout != null) layout.draw(canvas);
                    canvas.restore();
                }else{
                    canvas.save();
                    canvas.translate(i * 3 * mW / 4, 0);
                    StaticLayout layout = mLayoutList.get(i);
                    if (layout != null) layout.draw(canvas);
                    canvas.restore();
                }
            }
            if (invalidate) postInvalidate();
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private void startAnim() {
        if (mStrList.size() == 0)return;
        int mDur = 1000;//第一列动画的时间基数

        float x = 1+ (mStrList.size() * 0.06f < 0.1?0:mStrList.size() * 0.06f);
        x = x>1.30f?1.30f:x;
        mW = (float) ((getWidth() / mStrList.size())* x);
        mLayoutH = 0;
        TextPaint p = getPaint();
        p.setColor(getCurrentTextColor());

        for (int i = 0; i < mStrList.size(); i++) {
            if (!TextUtils.isEmpty(mStrList.get(i))) {
                StaticLayout layout = new StaticLayout(mStrList.get(i), p, (int) mW, Layout.Alignment.ALIGN_CENTER, mSpacingmult, 0.0F, true);
                mLayoutList.add(layout);
                Scroller scroller = new Scroller(getContext());
                mLayoutH = layout.getHeight();
                scroller.startScroll(0, 0, 0, -mLayoutH/2+10, mDur);
                mScrList.add(scroller);
            }
        }
    }

    public class ColumnAttribute{
        private String mString ;//生成的String
        private Scroller mScroller;//滚动类
        private StaticLayout mStaticLayout;//绘制String的Layout
        private boolean isScrollerEnable; //滚动是否执行
        private boolean isAdd; //是否是增加
    }
}
