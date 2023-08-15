package com.example.wanandroid.service;

import com.example.wanandroid.bean.BannerBean;
import com.example.wanandroid.bean.ChapterBean;
import com.example.wanandroid.bean.CollectArticleBean;
import com.example.wanandroid.bean.CommentBean;
import com.example.wanandroid.bean.CommentCountBean;
import com.example.wanandroid.bean.HomeArticleBean;
import com.example.wanandroid.bean.HotkeyBean;
import com.example.wanandroid.bean.MessageBean;
import com.example.wanandroid.bean.TopArticleBean;
import com.example.wanandroid.bean.WXArticleBean;
import com.example.wanandroid.bean.WXArticleChapterBean;
import com.example.wanandroid.bean.WebsiteBean;

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
 * @description: 跟文章相关的操作，包括获取文章、搜索和收藏等
 * @date: 2023/6/26
 **/
public interface WanAndroidService {


    /**
     * 获取Banner数据
     *
     * @return BannerBean
     */
    @GET("banner/json")
    Call<BannerBean> getBannerData();

    /**
     * 获取主页置顶文章
     *
     * @return TopArticleBean
     */
    @GET("article/top/json")
    Call<TopArticleBean> getTopArticleData();

    /**
     * 获取首页文章列表
     *
     * @param page 文章页码(0-40)，可以分页加载（可以用来实现懒加载）
     * @return HomeArticleBean
     */
    @GET("article/list/{page}/json")
    Call<HomeArticleBean> getHomeArticle(@Path("page") int page);

    /**
     * 获取搜索热词
     *
     * @return HotkeyBean
     */
    @GET("/hotkey/json")
    Call<HotkeyBean> getHotkeyData();

    /**
     * 搜索文章
     *
     * @param page 页数
     * @param k    关键词
     * @return HomeArticleBean
     */
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Call<HomeArticleBean> getHomeArticle(@Path("page") int page, @Field("k") String k);

    /**
     * 获取体系数据，一二级标题
     *
     * @return ChapterBean
     */
    @GET("/tree/json")
    Call<ChapterBean> getChapterData();

    /**
     * 通过chapter_id获取文章
     *
     * @param page 文章页数
     * @param cid  二级标题的id
     * @return HomeArticleBean
     */
    @GET("article/list/{page}/json")
    Call<HomeArticleBean> getArticleById(@Path("page") int page, @Query("cid") String cid);


    /**
     * 收藏站内文章
     *
     * @param id 文章id
     * @return MessageBean
     */
    @POST("lg/collect/{id}/json")
    Call<MessageBean> collectInnerArticle(@Path("id") int id);

    /**
     * 收藏站外文章
     *
     * @param title  标题
     * @param author 作者
     * @param link   链接
     * @return MessageBean
     */
    @POST("lg/collect/add/json")
    @FormUrlEncoded
    Call<MessageBean> collectOutArticle(@Field("title") String title, @Field("author") String author, @Field("link") String link);

    /**
     * 从展示的文章的列表（主页的那些文章列表）那里取消收藏文章
     *
     * @param id 文章id
     * @return MessageBean
     */
    @POST("lg/uncollect_originId/{id}/json")
    Call<MessageBean> uncollectArticleInList(@Path("id") int id);

    //TODO 有一些Service没用上

    /**
     * 从个人收藏界面取消收藏
     *
     * @param id       文章id，跟上面那个id不同，上面那个id对应的是收藏文章的originId
     * @param originId 文章真实的id，对应uncollectArticleInList()方法要传入的id
     * @return MessageBean
     */
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    Call<MessageBean> unCollectArticleInPerson(@Path("id") int id, @Field("originId") int originId);


    /**
     * 获取收藏文章
     *
     * @param page 页数
     * @return CollectArticleBean
     * @methodName getCollectArticle
     */
    @GET("lg/collect/list/{page}/json")
    Call<CollectArticleBean> getCollectArticle(@Path("page") int page);


    /**
     * 获取常用网站
     *
     * @return WebsiteBean
     */
    @GET("friend/json")
    Call<WebsiteBean> getWebsiteData();


    /**
     * 获取未读消息数量
     *
     * @return MessageBean
     */
    @GET("message/lg/count_unread/json")
    Call<CommentCountBean> getUnreadCommentsCount();


    /**
     * 获取未读消息列表
     *
     * @param page 页码 从1开始
     * @return CommentBean
     */
    @GET("message/lg/unread_list/{page}/json")
    Call<CommentBean> getUnreadComments(@Path("page") int page);

    /**
     * 获取已读消息列表
     *
     * @param page 页码 从1开始
     * @return CommentBean
     */
    @GET("message/lg/readed_list/{page}/json")
    Call<CommentBean> getReadComments(@Path("page") int page);

    /**
     * 获取微信公众号列表
     *
     * @return WXArticleChapterBean
     */
    @GET("wxarticle/chapters/json")
    Call<WXArticleChapterBean> getWXArticleChapters();

    /**
     * 获取公众号历史文章
     *
     * @param id   公众号id
     * @param page 页数
     * @return WXArticleBean
     */
    @GET("wxarticle/list/{id}/{page}/json")
    Call<WXArticleBean> getWXArticles(@Path("id") int id, @Path("page") int page);

}
