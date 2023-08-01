package com.example.wanandroid.base.search;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.wanandroid.R;


/**
 * @author Voyager
 * @className SearchActivity
 * @description 搜索界面
 * @date
 */

public class SearchActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    EditText search_input;
    ImageView imageView;
    TextView confirm_button;

    /**
     * EditView中的文本
     */
    String input;

    /**
     * 计数来判断是否在EditView中文本为空，在搜索过后清楚文本为空时展示热刺界面
     */
    private static int count = 0;

    /**
     * 文章页数
     */
    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.search_fragment_container, new HotkeyFragment())
                .commit();


    }

    /**
     * 点击Fragment中的热词后会执行这里的代码实现热刺内容自动填充
     *
     * @param text 点击的热词内容
     */
    public void onTextClicked(String text) {
        search_input.setText(text);
        input = text;
    }

    /**
     * 初始化控件
     */
    private void initView() {

        search_input = findViewById(R.id.search_input);
        imageView = findViewById(R.id.imageView);
        confirm_button = findViewById(R.id.confirm_button);

        search_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                input = s.toString();
                if (input.isEmpty() && count != 0) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.search_fragment_container, new HotkeyFragment())
                            .commit();
                }
            }
        });
        imageView.setOnClickListener((v) -> {
            finish();
        });
        confirm_button.setOnClickListener((v) -> {
            count++;

            SearchArticleFragment searchArticleFragment = new SearchArticleFragment();
            searchArticleFragment.setText(input);
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.search_fragment_container, searchArticleFragment)
                    .commit();

        });
    }

}










