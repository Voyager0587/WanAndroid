package com.example.wanandroid.utils;

import android.app.Application;

import org.litepal.LitePal;

/**
 * @className: App
 * @author: Voyager
 * @description: 获取App类的实例，从而获取全局的上下文和其他全局变量。
 * @date: 2023/7/8 12:14
 **/
public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LitePal.initialize(this);
    }

    public static App getInstance(){
        return instance;
    }
}
