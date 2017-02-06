package com.example.idea.customviewcollection.act;

import android.os.Bundle;

import com.example.idea.customviewcollection.R;

import butterknife.ButterKnife;

/**
 * Created by idea on 2017/1/4.
 * 特殊view事件处理
 */

public class SpecialViewEventActivity extends BaseActivity {




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_specialviewevent);

        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }


}
