package com.example.wanandroid.utils;

import com.example.wanandroid.bean.HotkeyBean;
import com.example.wanandroid.service.ProjectService;
import com.example.wanandroid.service.UserService;
import com.example.wanandroid.service.WanAndroidService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @className: HttpUtils
 * @author: Voyager
 * @description:
 * @date: 2023/6/25
 **/
public class HttpUtils {
    private static final String BASE_URL="https://www.wanandroid.com/";
    private static UserService userService;
    private static WanAndroidService wanAndroidService;
    private static ProjectService projectService ;
    private static Retrofit retrofit;
    private static HttpUtils httpUtils;
    private HttpUtils(){
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService=retrofit.create(UserService.class);
        wanAndroidService =retrofit.create(WanAndroidService.class);
        projectService=retrofit.create(ProjectService.class);
    }

    /**
     * 这段代码是一个单例模式的实现，用于获取HttpUtil类的唯一实例。如果实例已经存在，则直接返回该实例；否则创建一个新实例并返回。
     * 单例模式可以保证一个类在应用程序中只有一个实例，节约了系统资源，提高了系统性能。
     * 在这段代码中，使用了懒汉式的单例模式，即在需要使用时才创建实例，避免了在应用程序启动时就创建实例，浪费了系统资源的问题。
     * @return HttpUtils
     */
    public static HttpUtils getInstance(){
        if(httpUtils ==null) {
            return new HttpUtils();
        }
        else {
            return httpUtils;
        }
    }

    /**
     * 外部获取ProjectService的服务
     */
    public static ProjectService getProjectService() {
        return projectService;
    }

    /**
     * 外部获取UserService的服务
     */
    public static UserService getUserService(){
        return userService;
    }

    /**
     * 外部获取WanAndroidService的服务
     */
    public static WanAndroidService getwAndroidService() {
        return wanAndroidService;
    }

}
