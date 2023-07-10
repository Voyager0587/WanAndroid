package com.example.wanandroid.base;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.MessageBean;
import com.example.wanandroid.utils.HttpUtils;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @className WebActivity
 * @description 用网页展示文章的WebFragment的容器，负责收藏文章的逻辑
 * @author Voyager
 * @date 2023/7/8 17:45
 */

public class WebActivity extends AppCompatActivity {
    private ImageButton cancel, like;
    static final String BASE_URL = "https://www.wanandroid.com/";
    /**
     * 文章id
     */
    int id;
    /**
     * 文章链接，基链接，文章标题，文章作者
     */
    String url, baseUrl, title, author;

    /**
     * 是否是收藏文章
     * 文章原本的id
     * 文章是否收藏
     */
    int isCollectArticle,originId,judge=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web2);
        cancel = findViewById(R.id.cancel);
        like = findViewById(R.id.like);
        Intent intent = getIntent();

        url = intent.getStringExtra("url");
        id = intent.getIntExtra("id", -1);
        title = intent.getStringExtra("title");
        author = intent.getStringExtra("author");
        isCollectArticle=intent.getIntExtra("isCollectArticle",0);
        originId=intent.getIntExtra("originId",-1);
        baseUrl = url.substring(0, 26);
        WebFragment webFragment = WebFragment.newInstance(url);
        //TODO 接下来进行取消收藏功能编写★★★★★
        //为收藏界面的item.xml编写一个特殊的，加一个点亮的红色❤，点击后取消收藏，再点击相当于再次收藏
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_web, webFragment).commit();
        initListener();
        initView();

    }
    private void initView() {
        if(isCollectArticle==1){
            like.setBackgroundResource(R.drawable.like_icon_selected);
        }
    }

    private void initListener() {
        cancel.setOnClickListener(v -> {
            finish();
        });

        like.setOnClickListener(v -> {
            //收藏站内文章
            if(isCollectArticle==0){//不同文章列表
                if (url.contains("wanandroid") && url.indexOf("wanandroid") < 22) {
                    if (id != -1) {
                        Call<MessageBean> collectInnerCall = HttpUtils.getwAndroidService().collectInnerArticle(id);
                        collectInnerCall.enqueue(new Callback<MessageBean>() {
                            @Override
                            public void onResponse(@NonNull Call<MessageBean> call, @NonNull Response<MessageBean> response) {
                                Snackbar.make(like, "收藏成功", Snackbar.LENGTH_SHORT).show();
                                like.setBackgroundResource(R.drawable.like_icon_selected);


                            }

                            @Override
                            public void onFailure(@NonNull Call<MessageBean> call, @NonNull Throwable t) {
                                Snackbar.make(like, "收藏失败" + t.getMessage(), Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    }


                } else {
                    if (id != -1) {
                        Call<MessageBean> collectOutCall = HttpUtils.getwAndroidService().collectOutArticle(title, author, url);
                        collectOutCall.enqueue(new Callback<MessageBean>() {
                            @Override
                            public void onResponse(@NonNull Call<MessageBean> call, @NonNull Response<MessageBean> response) {
                                if (response.isSuccessful()) {
                                    Snackbar.make(like, "收藏成功", Snackbar.LENGTH_SHORT).show();
                                    like.setBackgroundResource(R.drawable.like_icon_selected);

                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<MessageBean> call, @NonNull Throwable t) {
                                Snackbar.make(like, "收藏失败" + t.getMessage(), Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Snackbar.make(like, "请求问题", Snackbar.LENGTH_SHORT).show();
                    }

                }
            }else {//收藏文章列表界面
                if(judge==1){
                    Call<MessageBean> call=HttpUtils.getwAndroidService().uncollectArticleInPerson(id,-1);
                    call.enqueue(new Callback<MessageBean>() {
                        @Override
                        public void onResponse(@NonNull Call<MessageBean> call, @NonNull Response<MessageBean> response) {
                            if(response.isSuccessful()){
                                Snackbar.make(like, "取消收藏成功", Snackbar.LENGTH_SHORT).show();
                                like.setBackgroundResource(R.drawable.like_icon);
                                judge=0;
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<MessageBean> call, @NonNull Throwable t) {
                            Snackbar.make(like,"取消收藏失败",Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    if (url.contains("wanandroid") && url.indexOf("wanandroid") < 22) {
                        if (id != -1) {
                            Call<MessageBean> collectInnerCall = HttpUtils.getwAndroidService().collectInnerArticle(originId);
                            collectInnerCall.enqueue(new Callback<MessageBean>() {
                                @Override
                                public void onResponse(@NonNull Call<MessageBean> call, @NonNull Response<MessageBean> response) {
                                    Snackbar.make(like, "收藏成功", Snackbar.LENGTH_SHORT).show();
                                    like.setBackgroundResource(R.drawable.like_icon_selected);


                                }

                                @Override
                                public void onFailure(@NonNull Call<MessageBean> call, @NonNull Throwable t) {
                                    Snackbar.make(like, "收藏失败" + t.getMessage(), Snackbar.LENGTH_SHORT).show();
                                }
                            });
                        }


                    } else {
                        if (id != -1) {
                            Call<MessageBean> collectOutCall = HttpUtils.getwAndroidService().collectOutArticle(title, author, url);
                            collectOutCall.enqueue(new Callback<MessageBean>() {
                                @Override
                                public void onResponse(@NonNull Call<MessageBean> call, @NonNull Response<MessageBean> response) {
                                    if (response.isSuccessful()) {
                                        Snackbar.make(like, "收藏成功", Snackbar.LENGTH_SHORT).show();
                                        like.setBackgroundResource(R.drawable.like_icon_selected);

                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<MessageBean> call, @NonNull Throwable t) {
                                    Snackbar.make(like, "收藏失败" + t.getMessage(), Snackbar.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Snackbar.make(like, "请求问题", Snackbar.LENGTH_SHORT).show();
                        }

                    }


                    judge=1;
                }


            }


        });
    }

}