package com.example.wanandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wanandroid.base.home.BlogActivity;
import com.example.wanandroid.bean.MessageBean;
import com.example.wanandroid.sharedPreference.SaveAccount;
import com.example.wanandroid.utils.HttpUtils;

import org.litepal.tablemanager.Connector;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Voyager
 * @className SplashScreen
 * @description 开屏动画界面
 * @date 2023/8/1
 */
public class SplashScreen extends AppCompatActivity {

    int judge = -1;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        HttpUtils.getInstance();
        int isAuto = SaveAccount.getIsAutoLogin(SplashScreen.this);
        Connector.getDatabase();
//        if (1 == isAuto) {
//            autoLogin();
//        }else {
//            setTimeout(2000);
//        }
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (1 == isAuto) {
                    autoLogin();
                } else {
                    setTimeout(2000);
                }
            }
        };
        timer.schedule(task, 0, 7000);

    }


    /**
     * 开屏动画
     *
     * @param time 持续时间
     */
    private void setTimeout(int time) {
        Looper.prepare();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                timer.cancel();
                finish();
            }
        }, time);
        Looper.loop();
    }

    /**
     * 自动登录请求
     */
    private void autoLogin() {
        Map<String, String> map = SaveAccount.getAccountInfo(SplashScreen.this);
        Call<MessageBean> call = HttpUtils.getUserService().login(map.get("account"), map.get("password"));
        call.enqueue(new Callback<MessageBean>() {
            @Override
            public void onResponse(@NonNull Call<MessageBean> call, @NonNull Response<MessageBean> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Intent intent;
                    if (response.body().getErrorCode() == 0) {
//                        judge = 1;
                        intent = new Intent(SplashScreen.this, BlogActivity.class);
                    } else {
                        Toast.makeText(SplashScreen.this, response.body().getErrorMsg(), Toast.LENGTH_SHORT).show();
                        intent = new Intent(SplashScreen.this, MainActivity.class);
                    }
                    timer.cancel();
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageBean> call, @NonNull Throwable t) {
                Toast.makeText(SplashScreen.this, "网络问题", Toast.LENGTH_SHORT).show();

            }
        });
    }

}