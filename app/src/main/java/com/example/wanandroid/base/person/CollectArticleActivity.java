package com.example.wanandroid.base.person;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.ArticleAdapter;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.CollectArticleBean;
import com.example.wanandroid.utils.HttpUtils;
import com.google.android.material.snackbar.Snackbar;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @className CollectArticleActivity
 * @description 个人收藏文章列表界面
 * @author Voyager
 * @date 2023/7/7
 */

public class CollectArticleActivity extends AppCompatActivity {

    private List<ArticleBean> collectArticleList = new ArrayList<ArticleBean>();
    private List<ArticleBean> loadCollectArticleList =new ArrayList<ArticleBean>();
    RecyclerView collectArticleRecyclerView;
    ArticleAdapter articleAdapter;
    LinearLayoutManager manager;
    SmartRefreshLayout refresh_layout;
    LinearLayout internet_error,blank_layout;
    /**
     * 要获取的文章所在页数
     */
    int page=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_article);
        initView();
        initRefreshLayout();
        getCollectArticle(0);

    }

    private void initRefreshLayout() {
        refresh_layout=findViewById(R.id.refresh_layout);
        refresh_layout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        refresh_layout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        refresh_layout.setOnRefreshListener(refresh_layout -> {
            refresh_layout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            collectArticleList.clear();
            page = 0;
            getCollectArticle(0);
            articleAdapter.notifyDataSetChanged();
        });

        refresh_layout.setOnLoadMoreListener(refresh_layout -> {
            refresh_layout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            page++;
            getCollectArticle(page);
            refresh_layout.finishLoadMore();

        });
    }

    private void initView() {
        collectArticleRecyclerView=findViewById(R.id.collectArticleRecyclerView);
        internet_error=findViewById(R.id.internet_error);
        blank_layout=findViewById(R.id.blank_layout);
    }


    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        manager=new LinearLayoutManager(this);
        collectArticleRecyclerView.setLayoutManager(manager);
        articleAdapter=new ArticleAdapter(collectArticleList);
        articleAdapter.setmContext(this);
        articleAdapter.setIsCollectArticle(1);
        collectArticleRecyclerView.setAdapter(articleAdapter);
        articleAdapter.notifyDataSetChanged();

    }

    /**
     * 获取收藏文章列表
     * @param page 文章页数
     */
    private void getCollectArticle(int page) {

        Call<CollectArticleBean> getCollectArticleCall= HttpUtils.getwAndroidService().getCollectArticle(page);
        getCollectArticleCall.enqueue(new Callback<CollectArticleBean>() {
            @Override
            public void onResponse(@NonNull Call<CollectArticleBean> call, @NonNull Response<CollectArticleBean> response) {
                assert response.body() != null;
                if(response.isSuccessful()){
                    internet_error.setVisibility(View.GONE);
                    CollectArticleBean collectArticleBean= response.body();
                    List<CollectArticleBean.DataBean.DatasBean> datasBeanList=collectArticleBean.getData().getDatas();
                    for (int i = 0; i < datasBeanList.size(); i++) {
                        ArticleBean articleBean=new ArticleBean();
                        articleBean.setAuthor(datasBeanList.get(i).getAuthor());
                        articleBean.setDate(datasBeanList.get(i).getNiceDate());
                        articleBean.setUrl(datasBeanList.get(i).getLink());
                        articleBean.setChapterName(datasBeanList.get(i).getChapterName());
                        articleBean.setTitle(datasBeanList.get(i).getTitle());
                        articleBean.setId(datasBeanList.get(i).getId());
                        articleBean.setOriginId(datasBeanList.get(i).getOriginId());
                        collectArticleList.add(articleBean);
                        if(page!=0){
                            loadCollectArticleList.add(articleBean);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(collectArticleList.size()==0){
                                    blank_layout.setVisibility(View.VISIBLE);
                                }else {
                                    blank_layout.setVisibility(View.GONE);
                                }
                                initRecyclerView();
                            }
                        });

                    }
                    if(loadCollectArticleList.size()==0){
                        Toast.makeText(CollectArticleActivity.this,"文章已经全部加载",Toast.LENGTH_SHORT).show();
                    }
                  loadCollectArticleList.clear();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CollectArticleBean> call, @NonNull Throwable t) {
                if(collectArticleList.size()==0){
                    internet_error.setVisibility(View.VISIBLE);
                }
                Snackbar.make(collectArticleRecyclerView,"获取收藏文章失败"+ t,Snackbar.LENGTH_SHORT).show();
            }
        });
    }


}