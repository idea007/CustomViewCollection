package com.example.idea.customviewcollection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.idea.customviewcollection.act.WoolglassActivity;
import com.example.idea.customviewcollection.adapter.PreviewInfoAdapter;
import com.example.idea.customviewcollection.model.PreviewInfo;
import com.example.idea.customviewcollection.utils.ActUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @see <a href="www.jianshu.com/p/54e8964730b4">注释语法</a>
 */
public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.rv_recyclerview)
    RecyclerView rv_recyclerview;

    private List<PreviewInfo> previewInfos;
//    private PreviewInfo previewInfo;
    private PreviewInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        ButterKnife.bind(this);

        initData();
        initView();


    }


    private void initData() {
        previewInfos = new ArrayList<>();
        previewInfos.add(new PreviewInfo("SerachView"));
        previewInfos.add(new PreviewInfo("ArrowDrawable"));
        previewInfos.add(new PreviewInfo("MagicLineView"));
        previewInfos.add(new PreviewInfo("BezierCurvesView"));
        previewInfos.add(new PreviewInfo("WaveView"));
        previewInfos.add(new PreviewInfo("SpecialViewEvent"));
        previewInfos.add(new PreviewInfo("LeafLoading"));
        previewInfos.add(new PreviewInfo("InkPageIndicator"));
        previewInfos.add(new PreviewInfo("DragBubbleView"));
        previewInfos.add(new PreviewInfo("FloatingView"));
        previewInfos.add(new PreviewInfo("Woolglass"));
    }

    private void initView() {
        adapter = new PreviewInfoAdapter(this, previewInfos);
        adapter.setOnClickListener(new PreviewInfoAdapter.OnClickListener() {

            @Override
            public void OnClick(int position, PreviewInfo previewInfo) {
                switch(position){
                    case 0:
                        ActUtils.toSearchViewActivity(HomeActivity.this, false);
                        break;
                    case 1:
                        ActUtils.toDrawerArrowDrawableActivity(HomeActivity.this,false);
                        break;
                    case 2:
                        ActUtils.toMagicLineActivity(HomeActivity.this,false);
                        break;
                    case 3:
                        ActUtils.toBezierCurvesActivity(HomeActivity.this,false);
                        break;
                    case 4:
                        ActUtils.toWaveViewActivity(HomeActivity.this,false);
                        break;
                    case 5:
                        ActUtils.toSpecialViewEventActivity(HomeActivity.this,false);
                        break;
                    case 6:
                        ActUtils.toLeafLoadingActivity(HomeActivity.this,false);
                        break;
                    case 7:
                        ActUtils.toInkPageIndicatorActivity(HomeActivity.this,false);
                        break;
                    case 8:
                        ActUtils.toDragBubbleActivity(HomeActivity.this,false);
                        break;
                    case 9:
                        ActUtils.toFloatingView(HomeActivity.this,false);
                        break;
                    case 10:
                        startActivity(new Intent(HomeActivity.this, WoolglassActivity.class));
                        break;
                    default:
                        break;
                }

            }
        });
        rv_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        rv_recyclerview.setAdapter(adapter);

    }

}
