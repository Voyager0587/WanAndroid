package com.example.wanandroid.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: SaveAcount
 * @author: Voyager
 * @description: 用来保存账号密码来实现自动登录
 * @date: 2023/6/25
 **/
public class SaveAcount {
    public static boolean saveAccountInfo(Context context, String account, String password){
        SharedPreferences sharedPreferences=context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("account", account);
        editor.putString("password", password);
        editor.apply();
        return true;
    }
    public static Map<String, String> getAccountInfo(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String account= sharedPreferences.getString("account", "");
        String password=sharedPreferences.getString("password", "");
        Map<String,String> map=new HashMap<String,String>();
        map.put("account", account);
        map.put("password", password);
        return map;
    }
}
