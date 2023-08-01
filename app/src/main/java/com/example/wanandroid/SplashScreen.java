package com.example.wanandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wanandroid.base.home.BlogActivity;
import com.example.wanandroid.bean.MessageBean;
import com.example.wanandroid.sharedPreference.SaveAccount;
import com.example.wanandroid.utils.HttpUtils;
import com.hanks.htextview.fade.FadeTextView;
import com.hanks.htextview.line.LineTextView;
import com.hanks.htextview.typer.TyperTextView;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @className SplashScreen
 * @description 开屏动画界面
 * @author Voyager
 * @date 2023/8/1
 */
public class SplashScreen extends AppCompatActivity {

    TyperTextView textView2;
    LineTextView textView3;
    int judge = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        textView3.animateText("WanAndroid");
        textView2.animateText("-Learn and create your own blog");
        HttpUtils.getInstance();
        int isAuto = SaveAccount.getIsAutoLogin(SplashScreen.this);
        if (1 == isAuto) {
            autoLogin();
        }
        setTimeout(2000);
    }


    /**
     * 开屏动画
     * @param time 持续时间
     */
    private void setTimeout(int time) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (1 == judge) {
                    intent = new Intent(SplashScreen.this, BlogActivity.class);
                } else {
                    intent = new Intent(SplashScreen.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, time);
    }

    /**
     * 自动登录请求
     */
    private void autoLogin() {
        Map<String, String> map=SaveAccount.getAccountInfo(SplashScreen.this);
        Call<MessageBean> call = HttpUtils.getUserService().login(map.get("account"), map.get("password"));
        call.enqueue(new Callback<MessageBean>() {
            @Override
            public void onResponse(@NonNull Call<MessageBean> call, @NonNull Response<MessageBean> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getErrorCode() == 0) {
                        judge = 1;
                    } else {
                        Toast.makeText(SplashScreen.this, response.body().getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageBean> call, @NonNull Throwable t) {
                Toast.makeText(SplashScreen.this,"网络问题", Toast.LENGTH_SHORT).show();

            }
        });
    }

}