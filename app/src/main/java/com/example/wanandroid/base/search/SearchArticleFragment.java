package com.example.wanandroid.base.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.HomeArticleBean;
import com.example.wanandroid.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchArticleFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    List<HomeArticleBean.DataBean.DatasBean> homeArticleBeanList=new ArrayList<>();
    private List<ArticleBean> articleBeanList=new ArrayList<>();
    private String author;

    public SearchArticleFragment() {
        // Required empty public constructor
    }

    /**
     * @param author Parameter 2.
     * @return A new instance of fragment SearchArticleFragment.
     */
    public static SearchArticleFragment newInstance(String author) {
        SearchArticleFragment fragment = new SearchArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, author);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_article, container, false);
    }

    /**
     * 通过作者名称搜索文章
     * @param text 搜索的内容
     */
    private void search(String text){
        Call<HomeArticleBean> call= HttpUtils.getwAndroidService().getHomeArticle(1,text);
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