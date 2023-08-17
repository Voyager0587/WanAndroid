package com.example.wanandroid.base.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.ArticleAdapter;
import com.example.wanandroid.adapter.BannerAdapter;
import com.example.wanandroid.adapter.ChapterAdapter;
import com.example.wanandroid.adapter.SuperChapterAdapter;
import com.example.wanandroid.base.search.SearchActivity;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.BannerBean;
import com.example.wanandroid.bean.ChapterBean;
import com.example.wanandroid.bean.HomeArticleBean;
import com.example.wanandroid.bean.TopArticleBean;
import com.example.wanandroid.utils.HttpUtils;
import com.example.wanandroid.utils.WrapContentLinearLayoutManager;
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
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @author Voyager
 * @className HomeTestFragment
 * @description 主页界面的Fragment
 * @date
 */

public class HomeTestFragment extends Fragment implements SuperChapterAdapter.OnSuperItemClickListener, ChapterAdapter.OnItemClickListener {

    SuperChapterAdapter superChapterAdapter;
    ChapterAdapter secondAdapter;
    BannerAdapter bannerAdapter;
    private ArticleAdapter articleAdapter;
    private WrapContentLinearLayoutManager manager;
    RecyclerView superChapter_recyclerview, chapter_recyclerview, article_recyclerview;
    List<ChapterBean.DataBean> data = new ArrayList<>();
    List<ChapterBean.DataBean.ChildrenBean> childrenBeanList = new ArrayList<>();
    private List<ArticleBean> articleBeanList = new ArrayList<>();
    private List<BannerBean.DataBean> bannerData = new ArrayList<>();
    //上拉加载的文章
    private List<ArticleBean> payload_articleBeanList = new ArrayList<>();
    private Banner banner;
    private RefreshLayout refreshLayout;
    private EditText search_input;
    private LinearLayout blank_layout, internet_error;
    private int chapterId = -100;
    private  Context context;

    /**
     * @param page 当前分类下文章的页数
     * @param currentPosition 记录当前选择的二级分类
     * @param currentId 记录上一次点击的二级分类id
     * @param superPosition 记录一级标题
     * @param temSecondaryPosition 记录上一次点击的二级分类，以便于调用notifyItemChanged(position)进行更新
     */
    int page = 0, currentPosition, currentId, superPosition, temSecondaryPosition = 0;

    public HomeTestFragment(Context context) {
        this.context = context;
    }

    public HomeTestFragment() {
        requireActivity().getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_test, container, false);
        superChapter_recyclerview = view.findViewById(R.id.superChapter_recyclerview);
        chapter_recyclerview = view.findViewById(R.id.chapter_recyclerview);
        article_recyclerview = view.findViewById(R.id.recyclerView_article);
        internet_error = view.findViewById(R.id.internet_error);
        banner = view.findViewById(R.id.banner);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        search_input = view.findViewById(R.id.search_input);
        blank_layout = view.findViewById(R.id.blank_layout);
        initListener();
        initChapter();
        getSuperChapterName();
        initRecyclerView();
        getDefaultArticle();
        initBannerData();
        initRefreshLayout();
        return view;
    }


    /**
     * 初始化控件监听器
     */
    private void initListener() {
        search_input.setCursorVisible(false);
        search_input.setFocusable(false);
        search_input.setFocusableInTouchMode(false);
        search_input.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), SearchActivity.class);
            ActivityOptionsCompat options1 = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(requireActivity(), search_input, "search_transition");
            startActivity(intent, options1.toBundle());

        });
    }

    /**
     * 初始化分类列表
     */
    private void initChapter() {
        ChapterBean.DataBean dataBean = new ChapterBean.DataBean("全部文章");
        List<ChapterBean.DataBean.ChildrenBean> childrenBeanList = new ArrayList<>();
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
        refreshLayout.setRefreshFooter(new BallPulseFooter(requireActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            page = 0;



            if (chapterId == -100) {
                getDefaultArticle();
            } else {
                getArticleById(String.valueOf(chapterId));
            }
            initBannerData();
            if (articleBeanList.size() != 0) {
                articleAdapter.notifyDataSetChanged();
            }
            refreshlayout.finishRefresh();
        });

        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            page++;
            payload_articleBeanList.clear();
            if (chapterId == -100) {
                getHomeArticleBeanList(page);
            } else {
                loadArticle(page, String.valueOf(chapterId));
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
                    requireActivity().runOnUiThread(() -> {
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
        superChapterAdapter = new SuperChapterAdapter(data);
        superChapterAdapter.setOnItemClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
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
        secondAdapter = new ChapterAdapter(data.get(position).getChildren());
        secondAdapter.setOnItemClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
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
        manager = new WrapContentLinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        article_recyclerview.setLayoutManager(manager);
        articleAdapter = new ArticleAdapter(articleBeanList,requireActivity());
        articleAdapter.setmContext(getActivity());
        articleAdapter.setArticleBeanList(articleBeanList);
        article_recyclerview.setAdapter(articleAdapter);
    }


    /**
     * 通过chapter_id获取文章
     *
     * @param id    二级标题的id
     */
    private void getArticleById(String id) {
        if (id == null) {
            return;
        }
        if (Integer.parseInt(id) != currentId) {
            articleBeanList.clear();
        }
        currentId = Integer.parseInt(id);
        Call<HomeArticleBean> call = HttpUtils.getwAndroidService().getArticleById(0, id);
        call.enqueue(new Callback<HomeArticleBean>() {
            @Override
            public void onResponse(@NonNull Call<HomeArticleBean> call, @NonNull Response<HomeArticleBean> response) {
                if (response.isSuccessful()) {
                    internet_error.setVisibility(View.GONE);
                    HomeArticleBean homeArticleBean = response.body();
                    if (homeArticleBean != null) {
                        if (homeArticleBean.getData().getDatas().size() == 0 && Objects.requireNonNull(response.body()).getErrorCode() == 0) {
                            Snackbar.make(blank_layout, "暂无相关数据", Snackbar.LENGTH_SHORT).show();
                        }
                        assert response.body() != null;
                        List<HomeArticleBean.DataBean.DatasBean> homeArticleBeanList = response.body().getData().getDatas();
                        articleBeanList.clear();
                        if (homeArticleBeanList.size() > 0) {
                            for (int i = 0; i < homeArticleBeanList.size(); i++) {
                                ArticleBean articleBean = new ArticleBean();
                                articleBean.setTitle(homeArticleBeanList.get(i).getTitle());
                                articleBean.setAuthor(homeArticleBeanList.get(i).getAuthor());
                                articleBean.setChapterName(homeArticleBeanList.get(i).getChapterName());
                                articleBean.setShareUser(homeArticleBeanList.get(i).getShareUser());
                                articleBean.setId(homeArticleBeanList.get(i).getId());
                                articleBean.setCollect(homeArticleBeanList.get(i).isCollect());
                                articleBean.setType(0);
                                articleBean.setUrl(homeArticleBeanList.get(i).getLink());
                                articleBean.setDate(homeArticleBeanList.get(i).getNiceDate());
                                articleBeanList.add(articleBean);
                                payload_articleBeanList.add(articleBean);
                            }
                            articleAdapter.setArticleBeanList(articleBeanList);
                                articleAdapter.notifyDataSetChanged();
                            if (articleBeanList.size() == 0) {
                                blank_layout.setVisibility(View.VISIBLE);
                            } else {
                                blank_layout.setVisibility(View.GONE);
                            }
                        }
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeArticleBean> call, @NonNull Throwable t) {
                if (articleBeanList.size() == 0) {
                    internet_error.setVisibility(View.VISIBLE);
                }
                Snackbar.make(article_recyclerview, "获取文章失败", Snackbar.LENGTH_SHORT).show();
            }
        });

    }


    /**
     * 加载文章
     *
     * @param cid 二级标题 id
     * @param pageGet 页数
     */
    private void loadArticle(int pageGet,String cid){
        Call<HomeArticleBean> call=HttpUtils.getwAndroidService().getArticleById(pageGet,cid);
        call.enqueue(new Callback<HomeArticleBean>() {
            @Override
            public void onResponse(@NonNull Call<HomeArticleBean> call, @NonNull Response<HomeArticleBean> response) {
                if(response.body()!=null){
                    payload_articleBeanList.clear();
                    List<HomeArticleBean.DataBean.DatasBean> homeArticleBeanList = response.body().getData().getDatas();
                    for(int i=0;i<homeArticleBeanList.size();i++){
                        ArticleBean articleBean=new ArticleBean();
                        articleBean.setTitle(homeArticleBeanList.get(i).getTitle());
                        articleBean.setAuthor(homeArticleBeanList.get(i).getAuthor());
                        articleBean.setChapterName(homeArticleBeanList.get(i).getChapterName());
                        articleBean.setShareUser(homeArticleBeanList.get(i).getShareUser());
                        articleBean.setId(homeArticleBeanList.get(i).getId());
                        articleBean.setCollect(homeArticleBeanList.get(i).isCollect());
                        articleBean.setType(0);
                        articleBean.setUrl(homeArticleBeanList.get(i).getLink());
                        articleBean.setDate(homeArticleBeanList.get(i).getNiceDate());
                        articleBeanList.add(articleBean);
                        payload_articleBeanList.add(articleBean);
                    }
                    if(payload_articleBeanList.isEmpty()) {
                        Toast.makeText(requireContext(),"没有更多数据了",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    requireActivity().runOnUiThread(() -> {
                        articleAdapter.setArticleBeanList(articleBeanList);
                        articleAdapter.notifyDataSetChanged();
//                        articleAdapter.notifyItemRangeInserted(articleBeanList.size(), payload_articleBeanList.size());
                    });
                }

            }

            @Override
            public void onFailure(@NonNull Call<HomeArticleBean> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(),"网络问题",Toast.LENGTH_SHORT).show();
                if (payload_articleBeanList.size() == 0 && pageGet != 0) {
                    page--;
                }
            }
        });
    }

















    /**
     * 获取默认文章
     */
    private void getDefaultArticle() {

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
                            articleBeanList.clear();
                            for (int i = 0; i < data.size(); i++) {
                                ArticleBean articleBean = new ArticleBean();
                                articleBean.setTitle(data.get(i).getTitle());
                                articleBean.setAuthor(data.get(i).getAuthor());
                                articleBean.setChapterName(data.get(i).getChapterName());
                                articleBean.setId(data.get(i).getId());
                                articleBean.setShareUser(data.get(i).getShareUser());
                                articleBean.setCollect(data.get(i).isCollect());
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
                            articleBean.setCollect(homeArticleBeanList.get(i).isCollect());
                            articleBean.setUrl(homeArticleBeanList.get(i).getLink());
                            articleBean.setDate(homeArticleBeanList.get(i).getNiceDate());
                            articleBeanList.add(articleBean);
                            payload_articleBeanList.add(articleBean);

                        }
                        requireActivity().runOnUiThread(() -> {
                            if (articleBeanList.size() == 0) {
                                blank_layout.setVisibility(View.VISIBLE);
                            } else {
                                internet_error.setVisibility(View.GONE);
                                blank_layout.setVisibility(View.GONE);
                            }

                        });
                            articleAdapter.notifyDataSetChanged();

                    }
                    if (payload_articleBeanList.size() == 0) {
                        page--;
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeArticleBean> call, @NonNull Throwable t) {
                if (articleBeanList.size() == 0) {
                    internet_error.setVisibility(View.VISIBLE);
                }
                if (payload_articleBeanList.size() == 0) {
                    page--;
                }
                Snackbar.make(article_recyclerview, "获取主页文章失败！", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 二级标题的点击事件
     *
     * @param view
     * @param position 点击的位置
     */
    @Override
    public void onItemClick(View view, int position) {
        secondAdapter.setSecondPosition(position);
        if (position == 0 && superPosition == 0) {
            childrenBeanList = data.get(0).getChildren();
            getDefaultArticle();
        }
        if (chapterId != childrenBeanList.get(position).getId()) {
            articleBeanList.clear();
            page = 0;
        }
        chapterId = childrenBeanList.get(position).getId();
        if (childrenBeanList.get(position).getId() != -100) {
            if(chapterId!=currentId){
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getArticleById(String.valueOf(chapterId));
                    }
                });
            }

        }
        String judge = "" + superPosition;
        currentPosition = position;
        secondAdapter.setSuperJudge(judge);
        temSecondaryPosition = Integer.parseInt(ChapterAdapter.getSecondJudge());
        secondAdapter.setSecondJudge("" + position);
        secondAdapter.notifyItemChanged(position);
        secondAdapter.notifyItemChanged(temSecondaryPosition);

    }

    /**
     * 一级标题的点击事件
     *
     * @param view
     * @param position 点击的位置
     */
    @Override
    public void onSuperItemClick(View view, int position) {

        childrenBeanList = data.get(position).getChildren();
        superPosition = position;
        secondAdapter.setSuperPosition(position);
        superChapterAdapter.notifyDataSetChanged();
        requireActivity().runOnUiThread(() -> {
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
                Snackbar.make(banner, "网络问题", Snackbar.LENGTH_SHORT).show();
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
                bannerAdapter = new BannerAdapter(bannerData);
                bannerAdapter.setContext(requireActivity());
//                banner.setBannerGalleryMZ(20,0.8f);
                banner.setAdapter(bannerAdapter).addBannerLifecycleObserver(getActivity())
                        .setIndicator(new RectangleIndicator(getActivity())) //线条指示器
                        .setIndicatorHeight(18)//设置indicator的高度
                        .setIndicatorSelectedColor(Color.parseColor("#F14F4F"))
                        .setIndicatorNormalColor(Color.parseColor("#CC979B9D"))
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