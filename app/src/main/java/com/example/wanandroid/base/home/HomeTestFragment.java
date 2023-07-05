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
import com.example.wanandroid.bean.TopArticleBean;
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

    //TODO 你既然记录了二级的，那一级的不是也会变吗？
    /**
     * @param page 当前分类下文章的页数
     * @param currentPosition 记录当前选择的二级分类
     * @param currentId 记录上一次点击的二级分类id
     * @param superPosition 记录一级标题
     */
    int page=0,currentPosition,currentId,superPosition;
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
        getDefaultArticle();
        initChapter();
        getSuperChapterName();
        return view;
    }

    /**
     * 初始化分类列表
     */
    private void initChapter() {
        ChapterBean.DataBean dataBean=new ChapterBean.DataBean("全部文章");
        List<ChapterBean.DataBean.ChildrenBean> childrenBeanList=new ArrayList<>();
        childrenBeanList.add(new ChapterBean.DataBean.ChildrenBean("默认"));
        childrenBeanList.get(0).setId(-100);
        dataBean.setChildren(childrenBeanList);
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
                    data.addAll(chapterBean.getData());
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
        superChapterAdapter.setOnItemClickListener(this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        superChapter_recyclerview.setLayoutManager(layoutManager);
        superChapter_recyclerview.setAdapter(superChapterAdapter);
        superChapterAdapter.notifyDataSetChanged();
        initChapterRecyclerView(0);

    }

    /**
     * 初始化二级分类列表，随一级而变化
     */
    private void initChapterRecyclerView(int position) {
        secondAdapter=new ChapterAdapter(data.get(position).getChildren());
        secondAdapter.setOnItemClickListener(this);
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
        if(id!=String.valueOf(currentId)){
            articleBeanList.clear();
        }
        currentId= Integer.parseInt(id);
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
     * 加载文章
     * @param page 加载的文章所在页数
     * @param id 分类id
     */
    private void loadArticle(int page,int id){

    }


    /**
     * 获取默认文章
     */
    private void getDefaultArticle(){
        articleBeanList.clear();
        Call<TopArticleBean> topArticleBeanCall = HttpUtils.getwAndroidService().getTopArticleData();
        topArticleBeanCall.enqueue(new Callback<TopArticleBean>() {
            @Override
            public void onResponse(@NonNull Call<TopArticleBean> call, @NonNull Response<TopArticleBean> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    TopArticleBean topArticleBean = response.body();
                    if (topArticleBean.getData() != null) {
                        List<TopArticleBean.DataBean> data = topArticleBean.getData();
                        if (data != null && data.size() > 0) {
                            for (int i = 0; i < data.size(); i++) {
                                ArticleBean articleBean = new ArticleBean();
                                articleBean.setTitle(data.get(i).getTitle());
                                articleBean.setAuthor(data.get(i).getAuthor());
                                articleBean.setChapterName(data.get(i).getChapterName());
                                articleBean.setShareUser(data.get(i).getShareUser());
                                articleBean.setType(1);
                                articleBean.setUrl(data.get(i).getLink());
                                articleBean.setDate(data.get(i).getNiceDate());
                                articleBeanList.add(articleBean);
                            }
                        }
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<TopArticleBean> call, @NonNull Throwable t) {
                Snackbar.make(article_recyclerview, "获取置顶文章失败！", Snackbar.LENGTH_SHORT).show();
            }
        });
        page = 0;
        getHomeArticleBeanList(page);
    }

    /**
     * @param pageGet 要获取的文章所在page
     * @methodName getHomeArticleBeanList
     * 获取主页文章
     */
    private void getHomeArticleBeanList(int pageGet) {
        int position = articleBeanList.size();
        if (pageGet != 0) {
            payload_articleBeanList.clear();
        }
        //TODO 有些可以传入page_size(1~40)，但page_size可能!=page,page的范围是0~40★
        Call<HomeArticleBean> homeArticleBeanCall = HttpUtils.getwAndroidService().getHomeArticle(pageGet);
        homeArticleBeanCall.enqueue(new Callback<HomeArticleBean>() {
            @Override
            public void onResponse(@NonNull Call<HomeArticleBean> call, @NonNull Response<HomeArticleBean> response) {
                if (response.isSuccessful()) {
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
                        if (page != 0) {
//                          articleAdapter.notifyItemInserted(payload_articleBeanList.size());
                            articleAdapter.notifyItemRangeInserted(articleBeanList.size(), payload_articleBeanList.size());
                            manager.scrollToPositionWithOffset(position - 3, 200);

                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeArticleBean> call, @NonNull Throwable t) {
                Snackbar.make(article_recyclerview, "获取主页文章失败！", Snackbar.LENGTH_SHORT).show();
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
        if(position==0&&superPosition==0){
            childrenBeanList=data.get(0).getChildren();
            getDefaultArticle();
        }
        if(childrenBeanList.get(position).getId()!=-100){
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getArticleById(page, String.valueOf(childrenBeanList.get(position).getId()));
                }
            });
        }


    }

    /**
     * 一级标题的点击事件
     * @param view
     * @param position 点击的位置
     */
    @Override
    public void onSuperItemClick(View view, int position) {

        childrenBeanList=data.get(position).getChildren();
        superPosition=position;
        requireActivity().runOnUiThread(()->{
            initChapterRecyclerView(position);
        });


    }


    //另一个就是点击一级标题不会自动加载文章，而是进一步点击第二级才会切换文章

}