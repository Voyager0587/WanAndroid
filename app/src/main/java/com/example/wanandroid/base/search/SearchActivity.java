package com.example.wanandroid.base.search;


import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.wanandroid.R;



/**
 * @author liukai
 */
public class SearchActivity extends AppCompatActivity {
    FragmentManager fragmentManager;

    EditText search_input;
    ImageView imageView;
    Button confirm_button;

    /**
     * EditView中的文本
     */
    String input;

    /**
     * 计数来判断是否在EditView中文本为空，在搜索过后清楚文本为空时展示热刺界面
     */
    private static int count=0;

    /**
     * 文章页数
     */
    int page=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.search_fragment_container,new HotkeyFragment())
                .commit();

    }
    public void onTextClicked(String text) {
        search_input.setText(text);
        input=text;
    }
    /**
     * 初始化控件
     */
    private void initView() {
        search_input = findViewById(R.id.search_input);
        imageView= findViewById(R.id.imageView);
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
                input=s.toString();
                if(input.isEmpty()&&count!=0){
                    //TODO 这边都要改成Hide和show的方法★★★
                    //TODO Banner的点击事件还没加★★
                    getSupportFragmentManager()
                                .beginTransaction()
                                .setReorderingAllowed(true)
                                .add(R.id.search_fragment_container,new HotkeyFragment())
                                .commit();
                }
            }
        });
        imageView.setOnClickListener((v) -> {
            finish();
        });
        confirm_button.setOnClickListener((v) -> {
            count++;

            SearchArticleFragment searchArticleFragment=new SearchArticleFragment();
            searchArticleFragment.setAuthor(input);
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.search_fragment_container,searchArticleFragment)
                    .commit();

        });
    }

}










