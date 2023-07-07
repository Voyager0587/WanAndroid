package com.example.wanandroid.base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.ArticleAdapter;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.CollectArticleBean;
import com.example.wanandroid.bean.MessageBean;
import com.example.wanandroid.bean.UserBean;
import com.example.wanandroid.utils.HttpUtils;
import com.google.android.material.snackbar.Snackbar;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @className CollectArticleActivity
 * @description 显示收藏的文章
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
        initRecyclerView();

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
            initRecyclerView();
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
    }


    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        manager=new LinearLayoutManager(this);
        collectArticleRecyclerView.setLayoutManager(manager);
        collectArticleRecyclerView.setAdapter(articleAdapter);
        articleAdapter=new ArticleAdapter(collectArticleList);
        articleAdapter.notifyDataSetChanged();

    }

    /**
     * 获取收藏文章列表
     * @param page 文章页数
     */
    private void getCollectArticle(int page) {
        UserBean userBean= LitePal.findFirst(UserBean.class);
        Call<CollectArticleBean> getCollectArticleCall= HttpUtils.getwAndroidService().getCollectArticle(page, userBean.getUsername(), userBean.getPassword());
        getCollectArticleCall.enqueue(new Callback<CollectArticleBean>() {
            @Override
            public void onResponse(@NonNull Call<CollectArticleBean> call, @NonNull Response<CollectArticleBean> response) {
                String mess=call.toString().trim();
                assert response.body() != null;
                String mess2=response.body().toString().trim();
                int a;
                if(response.isSuccessful()){
                    CollectArticleBean collectArticleBean= response.body();
                    List<CollectArticleBean.DataBean.DatasBean> datasBeanList=collectArticleBean.getData().getDatas();
                    for (int i = 0; i < datasBeanList.size(); i++) {
                        ArticleBean articleBean=new ArticleBean();
                        articleBean.setAuthor(datasBeanList.get(i).getAuthor());
                        articleBean.setDate(datasBeanList.get(i).getNiceDate());
                        articleBean.setUrl(datasBeanList.get(i).getLink());
                        articleBean.setChapterName(datasBeanList.get(i).getChapterName());
                        articleBean.setTitle(datasBeanList.get(i).getTitle());
                        collectArticleList.add(articleBean);
                        if(page!=0){
                            loadCollectArticleList.add(articleBean);

                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CollectArticleBean> call, @NonNull Throwable t) {
                Snackbar.make(collectArticleRecyclerView,"获取收藏文章失败"+t.toString(),Snackbar.LENGTH_SHORT).show();
            }
        });
    }


}