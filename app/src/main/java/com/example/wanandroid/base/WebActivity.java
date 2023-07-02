package com.example.wanandroid.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.wanandroid.R;

public class WebActivity extends AppCompatActivity {
    private ImageButton cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web2);
        cancel=(ImageButton)findViewById(R.id.cancel);
        Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        WebFragment webFragment= WebFragment.newInstance(url);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_web,webFragment).commit();
        cancel.setOnClickListener(v -> {
            finish();
        });
    }
}