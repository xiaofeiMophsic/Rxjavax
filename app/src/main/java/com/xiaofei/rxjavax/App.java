package com.xiaofei.rxjavax;

import android.app.Application;

/**
 * 作者：xiaofei
 * 日期：2016/10/10
 * 邮箱：paozi.xiaofei.123@gmail.com
 */

public class App extends Application {

    public static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
