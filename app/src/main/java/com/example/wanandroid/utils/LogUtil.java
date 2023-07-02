package com.example.wanandroid.utils;

import android.util.Log;

/**
 * @className: LogUtils
 * @author: Voyager
 * @description:
 * @date: 2023/6/25
 **/
public class LogUtil {
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    //最高级log的tag <它 以此清除log
    //log tag
    public static int level = VERBOSE;
    //后期改为NOTHING即可

    public static void v(String TAG, String MSG) {
        if (level <= VERBOSE) {
            Log.v(TAG, MSG);
        }
    }

    public static void d(String TAG, String MSG) {
        if (level <= DEBUG) {
            Log.d(TAG, MSG);
        }
    }

    public static void i(String TAG, String MSG) {
        if (level <= INFO) {
            Log.i(TAG, MSG);
        }
    }

    public static void w(String TAG, String MSG) {
        if (level <= WARN) {
            Log.w(TAG, MSG);
        }
    }

    public static void e(String TAG, String MSG) {
        if (level <= ERROR) {
            Log.e(TAG, MSG);
        }
    }
}