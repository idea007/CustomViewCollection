package com.example.idea.customviewcollection.act;

import android.os.Bundle;

import com.example.idea.customviewcollection.R;

import butterknife.ButterKnife;

/**
 * Created by idea on 2017/1/4.
 */

public class DragBubbleActivity extends BaseActivity {




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_dragbubble);

        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }


}
