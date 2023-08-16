package com.example.wanandroid.base.person;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.ArticleAdapter;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.HomeArticleBean;
import com.example.wanandroid.utils.HttpUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Voyager
 * @className WXArticleActivity
 * @description
 * @date 2023/8/16 14:50
 */

public class WXArticleActivity extends AppCompatActivity {

    SmartRefreshLayout refresh_layout;
    TextView name;
    ImageButton cancel;
    RecyclerView recyclerView;
    ArticleAdapter articleAdapter;
    private List<ArticleBean> articleBeanList = new ArrayList<>();
    private List<ArticleBean> payload_articleBeanList = new ArrayList<>();
    int id, page = 0;
    String s_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxarticle);
        Intent intent=getIntent();
        id=intent.getIntExtra("id",-1);
        s_name=intent.getStringExtra("name");
        initView();
        initListener();
        initRecyclerView();
        getWXArticles(id,page);
    }


    private void initListener() {
        cancel.setOnClickListener(v -> {
            finish();
        });

        refresh_layout.setOnRefreshListener(refreshLayout -> {

        });
        refresh_layout.setOnLoadMoreListener(refreshLayout -> {

        });
    }

    private void initRecyclerView() {
        articleAdapter=new ArticleAdapter(articleBeanList,WXArticleActivity.this);
        articleAdapter.setmContext(this);
        articleAdapter.tag=1;
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(articleAdapter);

    }

    private void initView() {
        cancel = findViewById(R.id.cancel);
        recyclerView = findViewById(R.id.wxArticleRecyclerView);
        refresh_layout = findViewById(R.id.refresh_layout);
        name = findViewById(R.id.wx_name);
        name.setText(s_name);

    }

    /**
     * 获取微信文章
     *
     * @param id   微信公众号id
     * @param page 页数
     */
    private void getWXArticles(int id, int page) {
        Call<HomeArticleBean> call = HttpUtils.getwAndroidService().getWXArticles(id, page);
        call.enqueue(new Callback<HomeArticleBean>() {
            @Override
            public void onResponse(@NonNull Call<HomeArticleBean> call, @NonNull Response<HomeArticleBean> response) {
                if(response.body()!=null){
                    List<HomeArticleBean.DataBean.DatasBean> homeArticleBeanList = response.body().getData().getDatas();
                    if (homeArticleBeanList.size() > 0) {
                        for (int i = 0; i < homeArticleBeanList.size(); i++) {
                            ArticleBean articleBean = new ArticleBean();
                            articleBean.setTitle(homeArticleBeanList.get(i).getTitle());
                            articleBean.setAuthor(homeArticleBeanList.get(i).getAuthor());
                            articleBean.setChapterName(homeArticleBeanList.get(i).getChapterName());
                            articleBean.setShareUser(homeArticleBeanList.get(i).getShareUser());
                            articleBean.setId(homeArticleBeanList.get(i).getId());
                            articleBean.setType(0);
                            articleBean.setCollect(homeArticleBeanList.get(i).isCollect());
                            articleBean.setUrl(homeArticleBeanList.get(i).getLink());
                            articleBean.setDate(homeArticleBeanList.get(i).getNiceDate());
                            articleBeanList.add(articleBean);
                            payload_articleBeanList.add(articleBean);

                        }

                        articleAdapter.setArticleBeanList(articleBeanList);
                        runOnUiThread(()->{
                            articleAdapter.notifyDataSetChanged();
                        });

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeArticleBean> call, @NonNull Throwable t) {

            }
        });
    }


}