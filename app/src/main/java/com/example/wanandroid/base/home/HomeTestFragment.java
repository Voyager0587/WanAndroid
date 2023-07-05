package com.example.wanandroid.base.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.ArticleAdapter;
import com.example.wanandroid.adapter.ChapterAdapter;
import com.example.wanandroid.adapter.SuperChapterAdapter;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.ChapterBean;
import com.example.wanandroid.bean.HomeArticleBean;
import com.example.wanandroid.utils.HttpUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeTestFragment extends Fragment implements SuperChapterAdapter.OnSuperItemClickListener, ChapterAdapter.OnItemClickListener {

    SuperChapterAdapter superChapterAdapter;
    ChapterAdapter secondAdapter;
    private ArticleAdapter articleAdapter;
    private LinearLayoutManager manager;
    RecyclerView superChapter_recyclerview, chapter_recyclerview,article_recyclerview;
    List<ChapterBean.DataBean> data = new ArrayList<>();
    List<ChapterBean.DataBean.ChildrenBean> childrenBeanList= new ArrayList<>();
    private List<ArticleBean> articleBeanList=new ArrayList<>();
    //上拉加载的文章
    private List<ArticleBean> payload_articleBeanList=new ArrayList<>();

    /**
     * @param page 当前分类下文章的页数
     * @param currentPosition 记录当前选择的二级分类
     */
    int page=0,currentPosition;
    public HomeTestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_test, container, false);
        superChapter_recyclerview = view.findViewById(R.id.superChapter_recyclerview);
        chapter_recyclerview = view.findViewById(R.id.chapter_recyclerview);
        article_recyclerview=view.findViewById(R.id.recyclerView_article);
        initChapter();
        getSuperChapterName();
        return view;
    }

    /**
     * 初始化分类列表
     */
    private void initChapter() {
        ChapterBean.DataBean dataBean=new ChapterBean.DataBean("全部文章");
        dataBean.getChildren().add(new ChapterBean.DataBean.ChildrenBean("默认"));
        data.add(dataBean);
    }

    /**
     * 获取标题
     */
    private void getSuperChapterName() {
        Call<ChapterBean> call = HttpUtils.getwAndroidService().getChapterData();
        call.enqueue(new Callback<ChapterBean>() {
            @Override
            public void onResponse(@NonNull Call<ChapterBean> call, @NonNull Response<ChapterBean> response) {
                if (response.isSuccessful()) {
                    ChapterBean chapterBean = response.body();
                    assert chapterBean != null;
                    data = chapterBean.getData();
                    requireActivity().runOnUiThread(()->{
                        initSuperRecyclerView();
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<ChapterBean> call, @NonNull Throwable t) {
                Snackbar.make(chapter_recyclerview, "获取文章失败", Snackbar.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * 初始化一级分类
     */
    private void initSuperRecyclerView() {
        superChapterAdapter =new SuperChapterAdapter(data);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        chapter_recyclerview.setLayoutManager(layoutManager);
        chapter_recyclerview.setAdapter(superChapterAdapter);
        superChapterAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化二级分类列表，随一级而变化
     */
    private void initChapterRecyclerView(int position) {
        secondAdapter=new ChapterAdapter(data.get(position).getChildren());
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.HORIZONTAL);
        chapter_recyclerview.setLayoutManager(manager);
        chapter_recyclerview.setAdapter(secondAdapter);
        secondAdapter.notifyDataSetChanged();
    }

    /**
     * @methodName initRecyclerView
     * 初始化RecyclerView
     */
    private void initRecyclerView() {

        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);
        article_recyclerview.setLayoutManager(manager);
        articleAdapter = new ArticleAdapter(articleBeanList);
        articleAdapter.setmContext(getActivity());
        article_recyclerview.setAdapter(articleAdapter);
        articleAdapter.notifyDataSetChanged();

    }



    private void getArticleById(int page,String id) {
        if(id ==null){
            //getHomeArticle();
            return;
        }
        Call<HomeArticleBean> call=HttpUtils.getwAndroidService().getArticleById(page,id);
        call.enqueue(new Callback<HomeArticleBean>() {
            @Override
            public void onResponse(Call<HomeArticleBean> call, Response<HomeArticleBean> response) {
                if(response.isSuccessful()){
                    HomeArticleBean homeArticleBean= response.body();
                    if(homeArticleBean!=null){
                        assert response.body() != null;
                        List<HomeArticleBean.DataBean.DatasBean> homeArticleBeanList = response.body().getData().getDatas();
                        if (homeArticleBeanList.size() > 0) {
                            for (int i = 0; i < homeArticleBeanList.size(); i++) {
                                ArticleBean articleBean = new ArticleBean();
                                articleBean.setTitle(homeArticleBeanList.get(i).getTitle());
                                articleBean.setAuthor(homeArticleBeanList.get(i).getAuthor());
                                articleBean.setChapterName(homeArticleBeanList.get(i).getChapterName());
                                articleBean.setShareUser(homeArticleBeanList.get(i).getShareUser());
                                articleBean.setType(0);
                                articleBean.setUrl(homeArticleBeanList.get(i).getLink());
                                articleBean.setDate(homeArticleBeanList.get(i).getNiceDate());
                                articleBeanList.add(articleBean);
                                payload_articleBeanList.add(articleBean);
                                requireActivity().runOnUiThread(() -> {
                                initRecyclerView();
                                });
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<HomeArticleBean> call, Throwable t) {
                Snackbar.make(article_recyclerview,"获取文章失败",Snackbar.LENGTH_SHORT).show();
            }
        });

    }




    /**
     * 二级标题的点击事件
     * @param view
     * @param position 点击的位置
     */
    @Override
    public void onItemClick(View view, int position) {
        if(this.currentPosition!=position){
            page=0;
        }
        getArticleById(page, String.valueOf(childrenBeanList.get(position).getId()));

    }

    /**
     * 一级标题的点击事件
     * @param view
     * @param position 点击的位置
     */
    @Override
    public void onSuperItemClick(View view, int position) {
        requireActivity().runOnUiThread(()->{
            initChapterRecyclerView(position);
        });
        childrenBeanList=data.get(position).getChildren();

    }
}