package com.example.idea.customviewcollection.act;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.idea.customviewcollection.R;
import com.example.idea.customviewcollection.view.AnimTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by idea on 2017/1/4.
 */

public class AnimTextViewActivity extends BaseActivity {



    @BindView(R.id.atv_text)
    AnimTextView atv_text;
    @BindView(R.id.btn_start)
    Button btn_start;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_animtextview);

        ButterKnife.bind(this);
        initView();
    }

    private int num=1990;
    private void initView() {

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atv_text.setText(String.valueOf(num),String.valueOf(num-1));
                num--;

            }
        });

    }


}
