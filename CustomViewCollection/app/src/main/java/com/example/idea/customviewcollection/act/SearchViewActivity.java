package com.example.idea.customviewcollection.act;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import com.example.idea.customviewcollection.R;
import com.example.idea.customviewcollection.view.SearchView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by idea on 2017/1/4.
 */

public class SearchViewActivity extends BaseActivity {

    @BindView(R.id.btn_start)
    Button btn_start;
    @BindView(R.id.btn_end)
    Button btn_end;
    @BindView(R.id.sv_searchview)
    SearchView sv_searchview;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_searchview);

        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv_searchview.start();
            }
        });

        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv_searchview.end();
            }
        });
    }


}
