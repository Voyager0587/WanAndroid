package com.example.wanandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.hanks.htextview.base.AnimationListener;
import com.hanks.htextview.base.HTextView;

public class SplashScreen extends AppCompatActivity {

    HTextView hTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //autoLogin
        setTimeout(2000);

    }
    private int setTimeout(int time) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, time);
        return 0;
    }
}