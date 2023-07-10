package com.example.wanandroid.base.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.ArticleAdapter;
import com.example.wanandroid.adapter.BannerAdapter;
import com.example.wanandroid.base.search.SearchActivity;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.BannerBean;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @description 本Fragment已弃用
 */
public class HomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final int ARTICLE = 1001;
    private static final int BANNER = 1002;
    private List<BannerBean.DataBean> bannerData;
    private List<ArticleBean> articleBeanList = new ArrayList<>();
    /**
     * 上拉加载的文章
     */
    private List<ArticleBean> payload_articleBeanList = new ArrayList<>();
    private ArticleAdapter articleAdapter;
    private LinearLayoutManager manager;
    private int page;
    private Banner banner;
    private RefreshLayout refreshLayout;
    private RelativeLayout top_layout;
    RecyclerView recyclerView;

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        banner = view.findViewById(R.id.banner);
        recyclerView = view.findViewById(R.id.recyclerView_article);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        top_layout = view.findViewById(R.id.top_layout);
        //设置 Header 为 贝塞尔雷达 样式
        top_layout.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), SearchActivity.class);
            requireActivity().startActivity(intent);
        });

        initRefreshLayout();
        initData();
        return view;
    }

    /**
     * 初始化refreshLayout,添加加载和刷新监听
     */
    private void initRefreshLayout() {
        refreshLayout.setRefreshHeader(new BezierRadarHeader(requireActivity()).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
//        refreshLayout.setRefreshHeader(new Head(requireActivity()));
        refreshLayout.setRefreshFooter(new BallPulseFooter(requireActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            page = 0;
            initData();
            articleAdapter.notifyDataSetChanged();
        });
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            page++;
            getHomeArticleBeanList(page);
            refreshlayout.finishLoadMore();

        });
    }

    /**
     * 初始化View
     *
     * @param choice 想要初始化的View
     */
    private void initView(int choice) {
        switch (choice) {
            case BANNER:
                initBannerView();
                break;
            case ARTICLE:
                initArticleView();
                break;
        }
    }

    /**
     * 初始化文章RecyclerView
     */
    private void initArticleView() {
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initRecyclerView();
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
                banner.setAdapter(new BannerAdapter(bannerData)).addBannerLifecycleObserver(getActivity())
                        .setBannerRound(10f) //圆角
                        .setIndicator(new RectangleIndicator(getActivity())) //线条指示器
                        .setIndicatorHeight(18)//设置indicator的高度
                        .setIndicatorWidth(18, 18) //选中下宽度是否一致
                        .setIndicatorGravity(IndicatorConfig.Direction.CENTER);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {

        Call<BannerBean> bannerBeanCall = HttpUtils.getwAndroidService().getBannerData();
        bannerBeanCall.enqueue(new Callback<BannerBean>() {
            @Override
            public void onResponse(@NonNull Call<BannerBean> call, @NonNull Response<BannerBean> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    bannerData = response.body().getData();
                    initView(BANNER);

                }
            }

            @Override
            public void onFailure(@NonNull Call<BannerBean> call, @NonNull Throwable t) {
                Snackbar.make(banner, "！！！！", Snackbar.LENGTH_SHORT).show();
            }
        });

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
                                articleBean.setId(data.get(i).getId());
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
                Snackbar.make(banner, "获取置顶文章失败！", Snackbar.LENGTH_SHORT).show();
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
                            articleBean.setId(homeArticleBeanList.get(i).getId());
                            articleBean.setType(0);
                            articleBean.setUrl(homeArticleBeanList.get(i).getLink());
                            articleBean.setDate(homeArticleBeanList.get(i).getNiceDate());
                            articleBeanList.add(articleBean);
                            payload_articleBeanList.add(articleBean);
                            requireActivity().runOnUiThread(() -> {
                                initView(ARTICLE);
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
                Snackbar.make(banner, "获取主页文章失败！", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * @methodName initRecyclerView
     * 初始化RecyclerView
     */
    private void initRecyclerView() {

        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        articleAdapter = new ArticleAdapter(articleBeanList);
        articleAdapter.setmContext(getActivity());
        recyclerView.setAdapter(articleAdapter);
        articleAdapter.notifyDataSetChanged();

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