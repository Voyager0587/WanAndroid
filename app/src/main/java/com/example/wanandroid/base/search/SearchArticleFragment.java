package com.example.wanandroid.base.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.ArticleAdapter;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.HomeArticleBean;
import com.example.wanandroid.utils.HttpUtils;
import com.google.android.material.snackbar.Snackbar;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Voyager
 * @className SearchArticleFragment
 * @description: 显示搜素的文章列表
 * @date
 */

public class SearchArticleFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    List<HomeArticleBean.DataBean.DatasBean> homeArticleBeanList = new ArrayList<>();
    private List<ArticleBean> payload_articleBeanList = new ArrayList<>();
    private List<ArticleBean> articleBeanList = new ArrayList<>();
    LinearLayout blank_layout;
    RecyclerView recyclerView;
    ArticleAdapter articleAdapter;
    SmartRefreshLayout refresh_layout_search;
    private LinearLayoutManager manager;
    //页数
    int page = 0;
    private String text;


    public SearchArticleFragment() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * @param text Parameter 2.
     * @return A new instance of fragment SearchArticleFragment.
     */
    public static SearchArticleFragment newInstance(String text) {
        SearchArticleFragment fragment = new SearchArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, text);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_article, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_search);
        refresh_layout_search = view.findViewById(R.id.refresh_layout_search);
        blank_layout=view.findViewById(R.id.blank_layout);
        search(text, page);
        initListener();
        return view;
    }

    private void initListener() {
        refresh_layout_search.setRefreshFooter(new BallPulseFooter(requireActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        refresh_layout_search.setOnRefreshListener(refreshLayout -> {
            refresh_layout_search.finishRefresh(700);//传入false表示刷新失败
            articleBeanList.clear();
            page = 0;
            search(text, page);
        });
        refresh_layout_search.setOnLoadMoreListener(refreshLayout -> {
            refresh_layout_search.finishLoadMore(700/*,false*/);//传入false表示加载失败
            page++;
            search(text, page);
            refresh_layout_search.finishLoadMore();
        });
    }

    private void initRecyclerView() {
        articleAdapter = new ArticleAdapter(getContext(), articleBeanList);
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(articleAdapter);
        articleAdapter.notifyDataSetChanged();

    }
    //TODO 拓展：回到顶部

    /**
     * 通过作者名称搜索文章
     *
     * @param text 搜索的内容
     */
    private void search(String text, int page) {
        int position = articleBeanList.size();
        Call<HomeArticleBean> call = HttpUtils.getwAndroidService().getHomeArticle(page, text);
        call.enqueue(new Callback<HomeArticleBean>() {
            @Override
            public void onResponse(@NonNull Call<HomeArticleBean> call, @NonNull Response<HomeArticleBean> response) {
                HomeArticleBean homeArticleBean = response.body();
                if (homeArticleBean.getData().getDatas().size() == 0&&Objects.requireNonNull(response.body()).getErrorCode()==0) {
                    Toast.makeText(getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                }
                if (homeArticleBean != null) {
                    homeArticleBeanList = homeArticleBean.getData().getDatas();
                    for (int i = 0; i < homeArticleBeanList.size(); i++) {
                        ArticleBean articleBean = new ArticleBean();
                        articleBean.setTitle(homeArticleBeanList.get(i).getTitle());
                        articleBean.setAuthor(homeArticleBeanList.get(i).getAuthor());
                        articleBean.setChapterName(homeArticleBeanList.get(i).getChapterName());
                        articleBean.setId(homeArticleBeanList.get(i).getId());
                        articleBean.setShareUser(homeArticleBeanList.get(i).getShareUser());
                        articleBean.setType(0);
                        articleBean.setUrl(homeArticleBeanList.get(i).getLink());
                        articleBean.setDate(homeArticleBeanList.get(i).getNiceDate());
                        articleBeanList.add(articleBean);

                        payload_articleBeanList.add(articleBean);
                        requireActivity().runOnUiThread(() -> {
                            initRecyclerView();
                        });
                        if (page != 0) {
                            articleAdapter.notifyItemRangeInserted(articleBeanList.size(), payload_articleBeanList.size());
                            manager.scrollToPositionWithOffset(position - 6, 0);
                            payload_articleBeanList.clear();
                        }
                    }
                }
                requireActivity().runOnUiThread(() -> {
                    if(articleBeanList.size()==0){
                        blank_layout.setVisibility(View.VISIBLE);
                    }else {
                        blank_layout.setVisibility(View.GONE);
                    }
                });

            }

            @Override
            public void onFailure(@NonNull Call<HomeArticleBean> call, Throwable t) {
                Snackbar.make(recyclerView, "获取文章失败", Snackbar.LENGTH_SHORT).show();
            }
        });

    }
}