package com.example.idea.customviewcollection.application;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by idea on 2017/1/4.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
