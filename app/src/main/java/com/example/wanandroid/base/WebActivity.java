package com.example.wanandroid.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.wanandroid.R;

public class WebActivity extends AppCompatActivity {
    private ImageButton cancel,like;
    static final String BASE_URL ="https://www.wanandroid.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web2);
        cancel= findViewById(R.id.cancel);
        like= findViewById(R.id.like);
        Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        String baseUrl=url.substring(0,26);
        WebFragment webFragment= WebFragment.newInstance(url);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_web,webFragment).commit();
        cancel.setOnClickListener(v -> {
            finish();
        });
        like.setOnClickListener(v -> {
            if(BASE_URL.equals(baseUrl)){
            //TODO cookie保存
            }else {

            }

        });
    }
}