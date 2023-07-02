package com.example.wanandroid.base.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.HotkeyAdapter;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.HomeArticleBean;
import com.example.wanandroid.bean.HotkeyBean;
import com.example.wanandroid.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    EditText search_input;
    RecyclerView hotkey_recyclerView;
    ImageView imageView;
    GridLayoutManager gridLayoutManager;
    private List<ArticleBean> articleBeanList=new ArrayList<>();
    List<HotkeyBean.DataBean> dataBeanList=new ArrayList<>();
    List<HomeArticleBean.DataBean.DatasBean> homeArticleBeanList=new ArrayList<>();
    LinearLayout layout;
    String input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        getHotkeyData();
        initRecyclerView();

    }

    private void initView() {
        search_input = findViewById(R.id.search_input);
        hotkey_recyclerView= findViewById(R.id.hotkey_recyclerView);
        imageView= findViewById(R.id.imageView);

        search_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                input=search_input.getText().toString();

            }
        });

    }

    private void initRecyclerView(){

        gridLayoutManager=new GridLayoutManager(this,4);
        hotkey_recyclerView.setLayoutManager(gridLayoutManager);
        hotkey_recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        hotkey_recyclerView.setHasFixedSize(true);
        hotkey_recyclerView.setNestedScrollingEnabled(false);
        hotkey_recyclerView.setAdapter(new HotkeyAdapter(dataBeanList));

    }

    private void getHotkeyData(){
        dataBeanList.clear();
        Call<HotkeyBean> call = HttpUtils.getwAndroidService().getHotkeyData();
        call.enqueue(new Callback<HotkeyBean>() {
            @Override
            public void onResponse(@NonNull Call<HotkeyBean> call, @NonNull Response<HotkeyBean> response) {
                if (response.isSuccessful()){
                    HotkeyBean hotkeyBean=response.body();
                    if(hotkeyBean!=null){
                        dataBeanList=hotkeyBean.getData();

                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<HotkeyBean> call, @NonNull Throwable t) {

            }
        });
    }
    private void search(String text){
        Call<HomeArticleBean> call=HttpUtils.getwAndroidService().getHomeArticle(1,text);
        call.enqueue(new Callback<HomeArticleBean>() {
            @Override
            public void onResponse(@NonNull Call<HomeArticleBean> call, @NonNull Response<HomeArticleBean> response) {
                HomeArticleBean homeArticleBean=response.body();
                if (homeArticleBean!=null){
                    homeArticleBeanList=homeArticleBean.getData().getDatas();
                    for (int i = 0; i < homeArticleBeanList.size(); i++) {
                        ArticleBean articleBean=new ArticleBean();
                        articleBean.setTitle(homeArticleBeanList.get(i).getTitle());
                        articleBean.setAuthor(homeArticleBeanList.get(i).getAuthor());
                        articleBean.setChapterName(homeArticleBeanList.get(i).getChapterName());
                        articleBean.setShareUser(homeArticleBeanList.get(i).getShareUser());
                        articleBean.setType(0);
                        articleBean.setUrl(homeArticleBeanList.get(i).getLink());
                        articleBean.setDate(homeArticleBeanList.get(i).getNiceDate());
                        articleBeanList.add(articleBean);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeArticleBean> call, Throwable t) {

            }
        });

    }

}