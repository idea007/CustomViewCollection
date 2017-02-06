package com.example.idea.customviewcollection.utils;

import android.app.Activity;
import android.content.Intent;

import com.example.idea.customviewcollection.act.BezierCurvesActivity;
import com.example.idea.customviewcollection.act.DragBubbleActivity;
import com.example.idea.customviewcollection.act.DrawerArrowDrawableActivity;
import com.example.idea.customviewcollection.act.FloatingViewActivity;
import com.example.idea.customviewcollection.act.InkPageIndicatorActivity;
import com.example.idea.customviewcollection.act.LeafLoadingActivity;
import com.example.idea.customviewcollection.act.MagicLineActivity;
import com.example.idea.customviewcollection.act.SearchViewActivity;
import com.example.idea.customviewcollection.act.SpecialViewEventActivity;
import com.example.idea.customviewcollection.act.WaveViewActivity;

/**
 * Created by idea on 2017/1/4.
 */

public class ActUtils {
    public static void toSearchViewActivity(Activity act, boolean isFinish){
        if(isFinish){
            act.finish();
        }
        act.startActivity(new Intent(act, SearchViewActivity.class));
    }

    public static void toDrawerArrowDrawableActivity(Activity act, boolean isFinish){
        if(isFinish){
            act.finish();
        }
        act.startActivity(new Intent(act, DrawerArrowDrawableActivity.class));
    }
    public static void toMagicLineActivity(Activity act, boolean isFinish){
        if(isFinish){
            act.finish();
        }
        act.startActivity(new Intent(act, MagicLineActivity.class));
    }
    public static void toBezierCurvesActivity(Activity act, boolean isFinish){
        if(isFinish){
            act.finish();
        }
        act.startActivity(new Intent(act, BezierCurvesActivity.class));
    }
    public static void toWaveViewActivity(Activity act, boolean isFinish){
        if(isFinish){
            act.finish();
        }
        act.startActivity(new Intent(act, WaveViewActivity.class));
    }
    public static void toSpecialViewEventActivity(Activity act, boolean isFinish){
        if(isFinish){
            act.finish();
        }
        act.startActivity(new Intent(act, SpecialViewEventActivity.class));
    }
    public static void toLeafLoadingActivity(Activity act, boolean isFinish){
        if(isFinish){
            act.finish();
        }
        act.startActivity(new Intent(act, LeafLoadingActivity.class));
    }
    public static void toInkPageIndicatorActivity(Activity act, boolean isFinish){
        if(isFinish){
            act.finish();
        }
        act.startActivity(new Intent(act, InkPageIndicatorActivity.class));
    }
    public static void toDragBubbleActivity(Activity act, boolean isFinish){
        if(isFinish){
            act.finish();
        }
        act.startActivity(new Intent(act, DragBubbleActivity.class));
    }
    public static void toFloatingView(Activity act, boolean isFinish){
        if(isFinish){
            act.finish();
        }
        act.startActivity(new Intent(act, FloatingViewActivity.class));
    }
}
