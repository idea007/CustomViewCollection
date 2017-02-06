package com.example.idea.customviewcollection.act;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.SeekBar;

import com.example.idea.customviewcollection.R;
import com.example.idea.customviewcollection.view.bezier.WaveView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by idea on 2017/1/4.
 */

public class WaveViewActivity extends BaseActivity {


    @BindView(R.id.sb_seekbar)
    SeekBar sb_seekbar;
    @BindView(R.id.wv_waveview)
    WaveView wv_waveview;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_waveview);

        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        sb_seekbar.setMax(100);
        sb_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                wv_waveview.setProgress(progress);
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
