package com.example.idea.customviewcollection.act;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.idea.customviewcollection.R;
import com.example.idea.customviewcollection.view.SearchView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by idea on 2017/1/4.
 */

public class MagicLineActivity extends BaseActivity {




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_magicline);

        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }


}
