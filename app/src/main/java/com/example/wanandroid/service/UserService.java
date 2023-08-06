package com.example.wanandroid.service;

import com.example.wanandroid.bean.MessageBean;
import com.example.wanandroid.bean.UserDataBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @className: UserService
 * @author: Voyager
 * @description: 跟用户相关的网络操作
 * @date: 2023/6/25
 **/
public interface UserService {

    /**
     * 登录请求
     *
     * @param passwordStr 储存数据的数据对象
     * @param usernameStr 储存数据的数据对象
     * @return MessageBean
     */
    @FormUrlEncoded
    @POST("user/login")
    Call<MessageBean> login(@Field("username") String usernameStr, @Field("password") String passwordStr);

    /**
     * 注册请求
     *
     * @param usernameStr      用户名
     * @param rePasswordString 第二次密码
     * @param passwordStr      密码
     * @return MessageBean
     */
    @FormUrlEncoded
    @POST("user/register")
    Call<MessageBean> register(@Field("username") String usernameStr, @Field("password") String passwordStr, @Field("repassword") String rePasswordString);

    /**
     * 退出登录
     *
     * @return MessageBean
     */
    @GET("user/logout/json")
    Call<MessageBean> logout();

    /**
     * 获取用户信息
     *
     * @return UserDataBean
     */
    @GET("/user/lg/userinfo/json")
    Call<UserDataBean> getUserData();

}
