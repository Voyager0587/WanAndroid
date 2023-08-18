package com.example.wanandroid.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.MessageBean;
import com.example.wanandroid.utils.HttpUtils;
import com.google.android.material.snackbar.Snackbar;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Voyager
 * @className WebActivity
 * @description 用网页展示文章的WebFragment的容器，负责收藏文章的逻辑
 * @date 2023/7/8 17:45
 */

public class WebActivity extends AppCompatActivity {

    private ImageButton cancel, like;
    /**
     * @param id 文章id,从正常的文章列表传入的是文章原本的id，从收藏页面传入的是另一个，只是便于寻找文章所在位置的id
     * @param originId 文章原本的id，便于进行收藏相关操作
     */
    int id, originId;

    /**
     * @description 便于进行站外文章收藏
     * @param url 文章链接
     * @param title 文章标题
     * @param author 文章作者
     * @param shareUser 文章分享人（转载）
     */
    String url, title, author,shareUser;

    /**
     * @param isCollectArticle 是否是收藏文章，只是用来标识是否是从个人收藏界面进入的WebActivity
     * @param judge 判断文章是否是收藏状态，便于实现在进入收藏文章界面后取消收藏，再点击收藏后可以收藏成功，其实是没看到文章JSON有collect这个属性呜呜呜
     */
    int isCollectArticle, tag, judge = 1;

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
        isCollectArticle = intent.getIntExtra("isCollectArticle", 0);
        originId = intent.getIntExtra("originId", -1);
        tag = intent.getIntExtra("tag", 0);
        shareUser=intent.getStringExtra("shareUser");
        WebFragment webFragment = WebFragment.newInstance(url);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_web, webFragment).commit();
        initListener();
        initView();

    }









    /**
     * 初始化控件状态
     */
    private void initView() {
        if (isCollectArticle == 1) {
            like.setBackgroundResource(R.drawable.like_icon_selected);
        }
        if (tag == 1) {
            like.setVisibility(View.GONE);
        }


    }

    /**
     * 初始化监听
     */
    private void initListener() {
        cancel.setOnClickListener(v -> {
            onBackPressed();
        });

        like.setOnClickListener(v -> {
            //收藏站内文章
            if (isCollectArticle == 0) {
                //普通文章列表（比如首页文章列表）进入WebActivity的的监听逻辑
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
            } else {
                //个人收藏文章列表进入进入WebActivity界面的监听逻辑
                if (judge == 1) {
                    Call<MessageBean> call = HttpUtils.getwAndroidService().unCollectArticleInPerson(id, -1);
                    call.enqueue(new Callback<MessageBean>() {
                        @Override
                        public void onResponse(@NonNull Call<MessageBean> call, @NonNull Response<MessageBean> response) {
                            if (response.isSuccessful()) {
                                Snackbar.make(like, "取消收藏成功", Snackbar.LENGTH_SHORT).show();
                                like.setBackgroundResource(R.drawable.like_icon);
                                judge = 0;
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<MessageBean> call, @NonNull Throwable t) {
                            Snackbar.make(like, "取消收藏失败", Snackbar.LENGTH_SHORT).show();
                        }
                    });
                } else {
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
                            Snackbar.make(like, "获取文章id失败", Snackbar.LENGTH_SHORT).show();
                        }

                    }

                    judge = 1;
                }


            }


        });
    }

}