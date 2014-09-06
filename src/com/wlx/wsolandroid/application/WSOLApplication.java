package com.wlx.wsolandroid.application;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;

public class WSOLApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MobclickAgent.openActivityDurationTrack(false);
        
    }
}
