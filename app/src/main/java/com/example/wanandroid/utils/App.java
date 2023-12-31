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

    public static App getInstance() {
        return instance;
    }





    /**
     * api问题：收藏和获取的文章数据中的collect不是实时同步的
     *
     *
     * 登录注册退出功能  √
     *全方位搜索功能测试 √ 断网之后搜索不显示文章，显示获取失败；重新连上网络，要等几秒再点击搜索
     * 全方位收藏功能测试  √ bug:不知道是不是api的额问题，从文章列表界面取消收藏会失败，而从收藏界面取消会成功
     * 全方位消息功能，公众号，常用网站(要不要加入一个写入粘贴板)测试  √
     * 全方位主页测试 √
     * 全方位项目测试 √
     * 网络逻辑测试 主页，项目，公众号，网站，消息，收藏，搜索的已测
     *
     *
     */

    /**
     *
     *
     * 8、29 最终测试顺序
     * 注册--》登录(自动登录)--》
     * 三个界面，主页、发现和个人
     * 先看主页
     * 主页
     * 1.文章查看，分类chapter
     * 2.收藏，取消收藏
     * 3.文章刷新加（附带网络测试）
     * 再看搜索
     * 搜索
     * 1.热词显示和点击
     * 2.搜索文章
     * 3.刷新和加载（附带网络测试）
     *
     * 发现
     * 1.项目chapter滑动展示项目
     * 2.项目点击
     * 3.项目刷新和加载（附带网络测试）
     *
     *个人（附带网络测试）
     * 1.收藏界面
     * 2.消息界面
     * 3.公众号
     * 4.网站
     * 5.关于
     * 6.注销
     *
     */
}
