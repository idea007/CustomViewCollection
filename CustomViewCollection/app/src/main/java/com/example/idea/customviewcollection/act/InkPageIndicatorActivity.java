package com.example.idea.customviewcollection.act;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.example.idea.customviewcollection.R;
import com.example.idea.customviewcollection.view.InkPageIndicator;
import com.example.idea.mylibrary.utils.DpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by idea on 2017/1/4.
 */

public class InkPageIndicatorActivity extends BaseActivity {


    @BindView(R.id.vp_viewpager)
    ViewPager vp_viewpager;
    @BindView(R.id.indicator)
    InkPageIndicator indicator;

    private MyAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_inkpageindicator);

        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        adapter=new MyAdapter(this);
        vp_viewpager.setAdapter(adapter);
        vp_viewpager.setPageMargin(DpUtils.dp2px(InkPageIndicatorActivity.this,16));

        indicator.setViewPager(vp_viewpager);


    }


    private class MyAdapter extends PagerAdapter{

        private Context context;
        public MyAdapter(Context context) {
            this.context=context;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            TextView tv=new TextView(context);
            tv.setBackgroundColor(context.getResources().getColor(R.color.wavecolor));
            container.addView(tv);
            return tv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object view) {
            container.removeView((View) view);
        }
    }


}
