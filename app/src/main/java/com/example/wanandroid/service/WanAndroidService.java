package com.example.wanandroid.service;

import com.example.wanandroid.bean.BannerBean;
import com.example.wanandroid.bean.HomeArticleBean;
import com.example.wanandroid.bean.HotkeyBean;
import com.example.wanandroid.bean.TopArticleBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @className: WanAndroidService
 * @author: Voyager
 * @description:
 * @date: 2023/6/26
 **/
public interface WanAndroidService {


    /**
     * 获取Banner数据
     * @return BannerBean
     */
    @GET("banner/json")
    Call<BannerBean> getBannerData();

    /**
     * 获取置顶文章
     * @return TopArticleBean
     */
    @GET("article/top/json")
    Call<TopArticleBean> getTopArticleData();

    /**
     * 获取首页的文章
     * @param page 文章页码(0-40)，可以分页加载（可以用来实现懒加载）
     * @return HomeArticleBean
     */
    @GET("article/list/{page}/json")
    Call<HomeArticleBean> getHomeArticle(@Path("page") int page);

    /**
     * 获取搜索热词
     * @return HotkeyBean
     */
    @GET("/hotkey/json")
    Call<HotkeyBean> getHotkeyData();

    /**
     * @param page 页数
     * @param k 作者名称
     * @return HomeArticleBean
     */
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Call<HomeArticleBean> getHomeArticle(@Path("page") int page, @Field("k") String k);






}
