package com.example.idea.customviewcollection.act;

import android.os.Bundle;

import com.example.idea.customviewcollection.R;

import butterknife.ButterKnife;

/**
 * Created by idea on 2017/1/4.
 */

public class BezierCurvesActivity extends BaseActivity {




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_beziercurves);

        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }


}
