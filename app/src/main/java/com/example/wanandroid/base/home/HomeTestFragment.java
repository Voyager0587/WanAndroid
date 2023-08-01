package com.example.wanandroid.base.home;

import  android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.ArticleAdapter;
import com.example.wanandroid.adapter.BannerAdapter;
import com.example.wanandroid.adapter.ChapterAdapter;
import com.example.wanandroid.adapter.SuperChapterAdapter;
import com.example.wanandroid.base.WebActivity;
import com.example.wanandroid.base.search.SearchActivity;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.BannerBean;
import com.example.wanandroid.bean.ChapterBean;
import com.example.wanandroid.bean.HomeArticleBean;
import com.example.wanandroid.bean.TopArticleBean;
import com.example.wanandroid.utils.HttpUtils;
import com.google.android.material.snackbar.Snackbar;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @className HomeTestFragment
 * @description 主页界面的Fragment
 * @author Voyager
 * @date
 */

public class HomeTestFragment extends Fragment implements SuperChapterAdapter.OnSuperItemClickListener, ChapterAdapter.OnItemClickListener {

    SuperChapterAdapter superChapterAdapter;
    ChapterAdapter secondAdapter;
    BannerAdapter bannerAdapter;
    private ArticleAdapter articleAdapter;
    private LinearLayoutManager manager;
    RecyclerView superChapter_recyclerview, chapter_recyclerview,article_recyclerview;
    List<ChapterBean.DataBean> data = new ArrayList<>();
    List<ChapterBean.DataBean.ChildrenBean> childrenBeanList= new ArrayList<>();
    private List<ArticleBean> articleBeanList=new ArrayList<>();
    private List<BannerBean.DataBean> bannerData=new ArrayList<>();
    //上拉加载的文章
    private List<ArticleBean> payload_articleBeanList=new ArrayList<>();
    private Banner banner;
    private RefreshLayout refreshLayout;
    private RelativeLayout top_layout;
    private LinearLayout blank_layout,internet_error;
    private int chapterId=-100;




    /**
     * @param page 当前分类下文章的页数
     * @param currentPosition 记录当前选择的二级分类
     * @param currentId 记录上一次点击的二级分类id
     * @param superPosition 记录一级标题
     * @param temSecondaryPosition 记录上一次点击的二级分类，以便于调用notifyItemChanged(position)进行更新
     */
    int page=0,currentPosition,currentId,superPosition,temSecondaryPosition=0;
    public HomeTestFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_test, container, false);
        superChapter_recyclerview = view.findViewById(R.id.superChapter_recyclerview);
        chapter_recyclerview = view.findViewById(R.id.chapter_recyclerview);
        article_recyclerview=view.findViewById(R.id.recyclerView_article);
        internet_error=view.findViewById(R.id.internet_error);
        banner = view.findViewById(R.id.banner);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        top_layout = view.findViewById(R.id.top_layout);
        blank_layout=view.findViewById(R.id.blank_layout);

        initListener();
        initChapter();
        getSuperChapterName();
        getDefaultArticle();
        initBannerData();
        initRefreshLayout();
        return view;
    }


    /**
     *初始化控件监听器
     */
    private void initListener() {

        top_layout.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), SearchActivity.class);
            requireActivity().startActivity(intent);
        });
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
     * 初始化refreshLayout,添加加载和刷新监听
     */
    private void initRefreshLayout() {

        refreshLayout.setRefreshHeader(new BezierRadarHeader(requireActivity()).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
        //refreshLayout.setRefreshHeader(new Head(requireActivity()));
        refreshLayout.setRefreshFooter(new BallPulseFooter(requireActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            refreshlayout.finishRefresh(700/*,false*/);//传入false表示刷新失败
            page = 0;

            initRecyclerView();
            articleBeanList.clear();
            bannerData.clear();
            if(chapterId==-100){
                getDefaultArticle();
            }else {
                getArticleById(page,String.valueOf(chapterId));
            }
            initBannerData();
            articleAdapter.notifyDataSetChanged();

        });

        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            refreshlayout.finishLoadMore(700/*,false*/);//传入false表示加载失败
            page++;
            payload_articleBeanList.clear();
            if(chapterId==-100){
                getHomeArticleBeanList(page);
            }else {
                getArticleById(page,String.valueOf(chapterId));
            }

            if(payload_articleBeanList.size()==0){
                page--;
            }
            refreshlayout.finishLoadMore();

        });
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



     /**
      * 通过chapter_id获取文章
     * @param page 文章页数
     * @param id 二级标题的id
     */
    private void getArticleById(int page,String id) {
        int position=articleBeanList.size();
        if(id ==null){
            //getHomeArticle();
            return;
        }

        if(Integer.parseInt(id)!=currentId){
            articleBeanList.clear();
        }

        currentId= Integer.parseInt(id);
        Call<HomeArticleBean> call=HttpUtils.getwAndroidService().getArticleById(page,id);
        call.enqueue(new Callback<HomeArticleBean>() {
            @Override
            public void onResponse(@NonNull Call<HomeArticleBean> call, @NonNull Response<HomeArticleBean> response) {
                if(response.isSuccessful()){
                    internet_error.setVisibility(View.GONE);
                    HomeArticleBean homeArticleBean= response.body();
                    if(homeArticleBean!=null){
                        if (homeArticleBean.getData().getDatas().size() == 0&& Objects.requireNonNull(response.body()).getErrorCode()==0) {
                            Snackbar.make(blank_layout,"没有更多数据了",Snackbar.LENGTH_SHORT).show();
                        }
                        assert response.body() != null;
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
                                articleBean.setUrl(homeArticleBeanList.get(i).getLink());
                                articleBean.setDate(homeArticleBeanList.get(i).getNiceDate());
                                articleBeanList.add(articleBean);
                                payload_articleBeanList.add(articleBean);
                                    requireActivity().runOnUiThread(() -> {
                                initRecyclerView();
                                });
                            }
                            if (page != 0) {
                                articleAdapter.notifyItemRangeInserted(articleBeanList.size(), payload_articleBeanList.size());
                                manager.scrollToPositionWithOffset(position - 3, 0);
                            }
                            if(page ==0&&articleBeanList.size()==0){
                                requireActivity().runOnUiThread(() -> {
                                    if(articleBeanList.size()==0){
                                        blank_layout.setVisibility(View.VISIBLE);
                                    }else {
                                        blank_layout.setVisibility(View.GONE);
                                    }

                                });
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeArticleBean> call, @NonNull Throwable t) {
                if(articleBeanList.size()==0){
                    internet_error.setVisibility(View.VISIBLE);
                }
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
                                articleBean.setId(data.get(i).getId());
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
                            articleBean.setId(homeArticleBeanList.get(i).getId());
                            articleBean.setType(0);
                            articleBean.setUrl(homeArticleBeanList.get(i).getLink());
                            articleBean.setDate(homeArticleBeanList.get(i).getNiceDate());
                            articleBeanList.add(articleBean);
                            payload_articleBeanList.add(articleBean);
                            requireActivity().runOnUiThread(() -> {
                                if(articleBeanList.size()==0){
                                    blank_layout.setVisibility(View.VISIBLE);
                                }else {
                                    blank_layout.setVisibility(View.GONE);
                                }
                                initRecyclerView();
                            });
                        }
                        if (pageGet != 0) {
                            //TODO 接下来修改二级分类UI
                            //TODO 收藏文章界面还要优化
                            articleAdapter.notifyItemRangeInserted(articleBeanList.size(), payload_articleBeanList.size());
                            manager.scrollToPositionWithOffset(position - 3,-30);

                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeArticleBean> call, @NonNull Throwable t) {
                if(articleBeanList.size()==0){
                    internet_error.setVisibility(View.VISIBLE);
                }
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
        secondAdapter.setSecondPosition(position);
        if(position==0&&superPosition==0){
            childrenBeanList=data.get(0).getChildren();
            getDefaultArticle();
        }
        if(chapterId!=childrenBeanList.get(position).getId()){
            articleBeanList.clear();
            page=0;
        }
        chapterId=childrenBeanList.get(position).getId();
        if(childrenBeanList.get(position).getId()!=-100){
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getArticleById(page, String.valueOf(chapterId));
                }
            });
        }
        String judge=""+superPosition;
        currentPosition=position;
        secondAdapter.setSuperJudge(judge);
        temSecondaryPosition=Integer.parseInt(ChapterAdapter.getSecondJudge());
        secondAdapter.setSecondJudge(""+position);
        secondAdapter.notifyItemChanged(position);
        secondAdapter.notifyItemChanged(temSecondaryPosition);

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
        secondAdapter.setSuperPosition(position);
        superChapterAdapter.notifyDataSetChanged();
        requireActivity().runOnUiThread(()->{
            initChapterRecyclerView(position);
        });

    }

    private void initBannerData() {
        Call<BannerBean> bannerBeanCall = HttpUtils.getwAndroidService().getBannerData();
        bannerBeanCall.enqueue(new Callback<BannerBean>() {
            @Override
            public void onResponse(@NonNull Call<BannerBean> call, @NonNull Response<BannerBean> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    bannerData = response.body().getData();
                    initBannerView();

                }
            }

            @Override
            public void onFailure(@NonNull Call<BannerBean> call, @NonNull Throwable t) {
                Snackbar.make(banner, "！！！！", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * @description 初始化Banner视图
     */
    private void initBannerView() {
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bannerAdapter=new BannerAdapter(bannerData);
                bannerAdapter.setContext(requireActivity());
                banner.setAdapter(bannerAdapter).addBannerLifecycleObserver(getActivity())
                        .setBannerRound(10f) //圆角
                        .setIndicator(new RectangleIndicator(getActivity())) //线条指示器
                        .setIndicatorHeight(18)//设置indicator的高度
                        .setIndicatorWidth(18, 18) //选中下宽度是否一致
                        .setIndicatorGravity(IndicatorConfig.Direction.CENTER);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        banner.destroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stop();
    }


}