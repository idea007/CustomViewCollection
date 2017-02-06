package com.example.idea.customviewcollection.act;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.idea.customviewcollection.R;
import com.example.idea.customviewcollection.view.DrawerArrowDrawable;
import com.example.idea.customviewcollection.view.SearchView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by idea on 2017/1/4.
 */

public class DrawerArrowDrawableActivity extends BaseActivity {

    @BindView(R.id.iv_image)
    ImageView iv_image;
    @BindView(R.id.sb_seekbar)
    SeekBar sb_seekbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_deawerarrowdrawable);

        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        final DrawerArrowDrawable drawable=new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return true;
            }
        };

        iv_image.setImageDrawable(drawable);
        sb_seekbar.setMax(100);
        sb_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                drawable.setProgress((float)progress/100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


}
