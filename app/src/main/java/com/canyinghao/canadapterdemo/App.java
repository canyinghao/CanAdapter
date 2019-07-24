package com.canyinghao.canadapterdemo;

import android.app.Application;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;


public class App extends MultiDexApplication {
    private static App sInstance;


    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;


    }

    public static App getInstance() {

        if (sInstance == null) {
            sInstance = new App();
        }
        return sInstance;
    }


    public void show(String msg){


        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

}