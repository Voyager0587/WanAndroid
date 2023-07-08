package com.example.wanandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wanandroid.service.ProjectService;
import com.example.wanandroid.service.UserService;
import com.example.wanandroid.service.WanAndroidService;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @className: HttpUtils
 * @author: Voyager
 * @description: 工具类，全局获取Service
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
        //OkHttpClient.Builder添加两个拦截器
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ReceivedCookiesInterceptor())
                .addInterceptor(new AddCookiesInterceptor())
                .build();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
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



    /**
     * @className ReceivedCookiesInterceptor
     * @description 接收拦截器，获取Cookie
     * @date 2023/7/8 12:23
     */
    public static class ReceivedCookiesInterceptor implements Interceptor {

        public ReceivedCookiesInterceptor() {
            super();
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            //这里获取请求返回的cookie
            if (!originalResponse.headers("Set-Cookie").isEmpty()) {

                HashSet<String> cookies = new HashSet<>();
                for(String header: originalResponse.headers("Set-Cookie"))
                {
                    LogUtil.i("拦截的cookie", "拦截的cookie是："+header);
                    cookies.add(header);
                }
                //保存的sharepreference文件名为cookieData
                SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences("cookieData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("cookie", cookies);

                editor.commit();
            }

            return originalResponse;
        }
    }

    /**
     * @className AddCookiesInterceptor
     * @description 发送拦截器，添加Cookie到请求头
     * @author Voyager 
     * @date 2023/7/8 12:23
     */
    public static class AddCookiesInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request.Builder builder = chain.request().newBuilder();
            HashSet<String> perferences = (HashSet) App.getInstance().getSharedPreferences("cookieData", Context.MODE_PRIVATE).getStringSet("cookie", null);
            if (perferences != null) {
                for (String cookie : perferences) {
                    builder.addHeader("Cookie", cookie);
                }
            }
            return chain.proceed(builder.build());
        }
    }



}
