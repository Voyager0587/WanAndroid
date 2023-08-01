package com.example.wanandroid.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: SaveAccount
 * @author: Voyager
 * @description: 用来保存账号密码来实现自动登录
 * @date: 2023/6/25
 **/
public class SaveAccount {


    /**
     *储存密码到本地
     * @param context 上下文
     * @param account 账号
     * @param password 密码
     * @return boolean 是否成功
     */
    public static boolean saveAccountInfo(Context context, String account, String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("account", account);
        editor.putString("password", password);
        editor.apply();
        return true;
    }


    /**
     * 获取账号和密码
     * @param context 上下文
     * @return Map<String, String> 储存着账号密码
     */
    public static Map<String, String> getAccountInfo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String account = sharedPreferences.getString("account", "");
        String password = sharedPreferences.getString("password", "");
        Map<String, String> map = new HashMap<String, String>();
        map.put("account", account);
        map.put("password", password);
        return map;
    }

    /**
     * 打开自动登录
     * @param context 上下文
     * @return boolean
     */
    public static boolean openAutoLogin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("isAutoLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("isAutoLogin", "1");
        editor.apply();
        return true;
    }

    /**
     * 关闭自动登录
     * @param context 上下文
     * @return boolean
     */
    public static boolean stopAutoLogin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("isAutoLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("isAutoLogin", "0");
        editor.apply();
        return true;
    }

    /**
     * 获取是否开启了自动登录
     * @param context 上下文
     * @return int
     */
    public static int getIsAutoLogin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("isAutoLogin", Context.MODE_PRIVATE);
        String isAutoLogin = sharedPreferences.getString("isAutoLogin", "-1");
        return Integer.parseInt(isAutoLogin);
    }

    /**
     * 清楚本地保存的用户数据
     * @param context 上下文
     * @return boolean 是否成功
     */
    public static boolean clearUpUserData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        sharedPreferences = context.getSharedPreferences("isAutoLogin", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        return true;
    }
}
