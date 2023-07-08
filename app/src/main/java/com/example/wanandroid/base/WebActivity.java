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
    int id;
    String url, baseUrl, title, author;

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
        baseUrl = url.substring(0, 26);
        WebFragment webFragment = WebFragment.newInstance(url);
        //TODO 接下来进行取消收藏功能编写，在这之前先push一下？
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_web, webFragment).commit();
        initListener();

    }

    private void initListener() {
        cancel.setOnClickListener(v -> {
            finish();
        });
        like.setOnClickListener(v -> {
            //收藏站内文章
            if (url.contains("wanandroid") && url.indexOf("wanandroid") < 22) {
                if (id != -1) {
                    Call<MessageBean> collectInnerCall = HttpUtils.getwAndroidService().collectInnerArticle(id);
                    collectInnerCall.enqueue(new Callback<MessageBean>() {
                        @Override
                        public void onResponse(@NonNull Call<MessageBean> call, @NonNull Response<MessageBean> response) {
                            Snackbar.make(like, "收藏成功", Snackbar.LENGTH_SHORT).show();

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

        });
    }
}