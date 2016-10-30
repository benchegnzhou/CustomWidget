package com.itcast.zbc.customwidge;

import android.app.Application;
import android.content.Context;


/**
 * Created by Zbc on 2016/7/29.
 */
public class mAppAplication extends Application {
    /**
     * 定义自己的全局变量方便使用
     */
   public static Context mAppcontext;
    @Override
    public void onCreate() {
        super.onCreate();
          mAppcontext = getApplicationContext();
    }
}
