package com.example.wanandroid.base.person;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Voyager
 * @className CollectArticleActivity
 * @description 个人收藏文章列表界面
 * @date 2023/7/7
 */

public class CollectArticleActivity extends AppCompatActivity {

    private List<ArticleBean> collectArticleList = new ArrayList<ArticleBean>();
    private List<ArticleBean> loadCollectArticleList = new ArrayList<ArticleBean>();
    RecyclerView collectArticleRecyclerView;
    ArticleAdapter articleAdapter;
    LinearLayoutManager manager;
    SmartRefreshLayout refresh_layout;
    LinearLayout internet_error, blank_layout;
    ImageButton cancel;
    /**
     * 要获取的文章所在页数
     */
    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_article);
        initView();
        initRecyclerView();
        initRefreshLayout();
        getCollectArticle(0);

    }

    private void initRefreshLayout() {
        refresh_layout = findViewById(R.id.refresh_layout);
        refresh_layout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        refresh_layout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        refresh_layout.setOnRefreshListener(refresh_layout -> {
            page = 0;
            getCollectArticle(0);


        });
        refresh_layout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getCollectArticle(page);
                refresh_layout.finishLoadMore();
            }
        });

    }

    private void initView() {
        cancel = findViewById(R.id.cancel);
        collectArticleRecyclerView = findViewById(R.id.collectArticleRecyclerView);
        internet_error = findViewById(R.id.internet_error);
        blank_layout = findViewById(R.id.blank_layout);
        cancel.setOnClickListener(v -> {
            finish();
        });
    }


    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        manager = new LinearLayoutManager(this);
        collectArticleRecyclerView.setLayoutManager(manager);
        articleAdapter = new ArticleAdapter(collectArticleList,CollectArticleActivity.this);
        articleAdapter.setmContext(this);
        articleAdapter.setIsCollectArticle(1);
        collectArticleRecyclerView.setAdapter(articleAdapter);
    }

    /**
     * 获取收藏文章列表
     *
     * @param pageGet 文章页数
     */
    private void getCollectArticle(int pageGet) {
        loadCollectArticleList.clear();
        Call<CollectArticleBean> getCollectArticleCall = HttpUtils.getwAndroidService().getCollectArticle(page);
        getCollectArticleCall.enqueue(new Callback<CollectArticleBean>() {
            @Override
            public void onResponse(@NonNull Call<CollectArticleBean> call, @NonNull Response<CollectArticleBean> response) {
                assert response.body() != null;
                if (response.isSuccessful()) {
                    internet_error.setVisibility(View.GONE);
                    CollectArticleBean collectArticleBean = response.body();
                    if(refresh_layout.isRefreshing()) {
                        collectArticleList.clear();
                    }
                    if (collectArticleBean.getData().getDatas().size() == 0 && Objects.requireNonNull(response.body()).getErrorCode() == 0&&pageGet==0) {
                        collectArticleList.clear();
                        articleAdapter.notifyDataSetChanged();
                        blank_layout.setVisibility(View.VISIBLE);
                        refresh_layout.finishRefresh();
                        return;
                    }
                    List<CollectArticleBean.DataBean.DatasBean> datasBeanList = collectArticleBean.getData().getDatas();

                    for (int i = 0; i < datasBeanList.size(); i++) {
                        ArticleBean articleBean = new ArticleBean();
                        articleBean.setAuthor(datasBeanList.get(i).getAuthor());
                        articleBean.setDate(datasBeanList.get(i).getNiceDate());
                        articleBean.setUrl(datasBeanList.get(i).getLink());
                        articleBean.setChapterName(datasBeanList.get(i).getChapterName());
                        articleBean.setTitle(datasBeanList.get(i).getTitle());
                        articleBean.setId(datasBeanList.get(i).getId());
                        articleBean.setCollect(true);
                        articleBean.setOriginId(datasBeanList.get(i).getOriginId());
                        collectArticleList.add(articleBean);
                        if (pageGet != 0) {
                            loadCollectArticleList.add(articleBean);
                        }
                        runOnUiThread(() -> {
                            blank_layout.setVisibility(View.GONE);
                            initRecyclerView();
                            articleAdapter.notifyDataSetChanged();
                        });

                    }
                    if (loadCollectArticleList.size() == 0 && pageGet != 0) {
                        page--;
                        Toast.makeText(CollectArticleActivity.this,"没有更多数据了",Toast.LENGTH_SHORT).show();
                    }
                    refresh_layout.finishRefresh();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CollectArticleBean> call, @NonNull Throwable t) {
                if (collectArticleList.size() == 0) {
                    internet_error.setVisibility(View.VISIBLE);
                }
                if (loadCollectArticleList.size() == 0 && pageGet != 0) {
                    page--;
                }
                refresh_layout.finishRefresh();
                Toast.makeText(CollectArticleActivity.this,"网络问题",Toast.LENGTH_SHORT).show();
            }
        });
    }


}