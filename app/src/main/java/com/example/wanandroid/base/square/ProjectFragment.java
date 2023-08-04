package com.example.wanandroid.base.square;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.ArticleAdapter;
import com.example.wanandroid.adapter.ProjectAdapter;
import com.example.wanandroid.bean.ProjectBean;
import com.example.wanandroid.utils.HttpUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Voyager
 * @className ProjectFragment
 * @description 项目界面
 * @date 2023/7/10 17:04
 */

public class ProjectFragment extends Fragment implements ArticleAdapter.BackToTopListener {

    RecyclerView projectRecyclerView;
    ProjectAdapter projectAdapter;
    SmartRefreshLayout refreshLayout;
    LinearLayout blank_layout, internet_error;
    FloatingActionButton backToTop;
    LinearLayoutManager manager;
    /**
     * @param data 储存项目数据列表
     * @param loadMoreData 储存加载的项目
     */
    List<ProjectBean.DataBean.DatasBean> data = new ArrayList<>();
    List<ProjectBean.DataBean.DatasBean> loadMoreData = new ArrayList<>();
    /**
     * @param id 分类id
     * @param page 文章获取的页数
     */
    int id, page;
    private static final String ARG_PARAM1 = "param1";


    public ProjectFragment(int id) {
        this.id = id;
    }

    public static ProjectFragment newInstance(int id) {
        ProjectFragment fragment = new ProjectFragment(id);
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        projectRecyclerView = view.findViewById(R.id.project_recyclerView);
        blank_layout = view.findViewById(R.id.blank_layout);
        internet_error = view.findViewById(R.id.internet_error);
        backToTop = view.findViewById(R.id.backToTop);
        page = 1;
        initData();
        initRefreshLayout();
        initRecyclerView();
        initListener();
        return view;
    }


    private void initListener() {
        backToTop.setOnClickListener(v -> {
            manager.scrollToPosition(0);
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {

        Call<ProjectBean> projectBeanCall = HttpUtils.getProjectService().getProjectList(page, id);
        projectBeanCall.enqueue(new Callback<ProjectBean>() {
            @Override
            public void onResponse(@NonNull Call<ProjectBean> call, @NonNull Response<ProjectBean> response) {
                if (response.isSuccessful()) {
                    internet_error.setVisibility(View.GONE);
                    ProjectBean projectBean = response.body();
                    if (projectBean != null && projectBean.getData() != null) {
                        data = projectBean.getData().getDatas();
                        projectAdapter = new ProjectAdapter(data);
                        requireActivity().runOnUiThread(() -> {
                            blank_layout.setVisibility(View.GONE);
                            initRecyclerView();
                            projectAdapter.notifyDataSetChanged();
                            if (data.size() == 0) {
                                blank_layout.setVisibility(View.VISIBLE);
                            }
                        });

                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<ProjectBean> call, Throwable t) {
                Snackbar.make(refreshLayout, "error:网络问题", Snackbar.LENGTH_SHORT).show();
                requireActivity().runOnUiThread(() -> {
                    if (data.size() == 0 && page == 1) {
                        internet_error.setVisibility(View.VISIBLE);
                    } else if (data.size() != 0) {
                        internet_error.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    /**
     * @param pageGet 获取的数据所在的页数
     * @methodName loadMoreData
     * @description 获取更多数据
     */
    public void loadMoreData(int pageGet) {
        loadMoreData.clear();
        Call<ProjectBean> projectBeanCall = HttpUtils.getProjectService().getProjectList(pageGet, id);
        projectBeanCall.enqueue(new Callback<ProjectBean>() {
            @Override
            public void onResponse(@NonNull Call<ProjectBean> call, @NonNull Response<ProjectBean> response) {
                if (response.isSuccessful()) {
                    ProjectBean projectBean = response.body();
                    if (projectBean.getData().getDatas().size() == 0 && Objects.requireNonNull(response.body()).getErrorCode() == 0) {
                        Snackbar.make(refreshLayout, "没有更多数据了", Snackbar.LENGTH_SHORT).show();
                    }
                    if (projectBean != null && projectBean.getData() != null) {
                        loadMoreData.addAll(projectBean.getData().getDatas());
                        data.addAll(projectBean.getData().getDatas());
                        projectAdapter.notifyItemRangeInserted(data.size(), loadMoreData.size());


                    }
                    if (loadMoreData.size() == 0 && pageGet != 0) {
                        page--;
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProjectBean> call, @NonNull Throwable t) {
                if (loadMoreData.size() == 0 && pageGet != 0) {
                    page--;
                }
                Snackbar.make(refreshLayout, "error:获取数据失败! ", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void initRecyclerView() {
        projectAdapter = new ProjectAdapter(data);
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);
        projectAdapter.setBackToTopListener(this);
        projectAdapter.setContext(getContext());
        projectRecyclerView.setLayoutManager(manager);
        projectRecyclerView.setAdapter(projectAdapter);

    }

    /**
     * 初始化refreshLayout,添加加载和刷新监听
     */
    private void initRefreshLayout() {
        refreshLayout.setRefreshHeader(new BezierRadarHeader(requireActivity()).setEnableHorizontalDrag(true));
        refreshLayout.setRefreshFooter(new BallPulseFooter(requireActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(refreshlayout1 -> {
            page = 1;
            initData();
            refreshlayout1.finishRefresh();
        });
        refreshLayout.setOnLoadMoreListener(refreshlayout1 -> {
            page++;
            loadMoreData(page);
            refreshlayout1.finishLoadMore();
        });
    }

    @Override
    public void onBackToTop(int position) {
        if (position >= 8) {
            backToTop.setVisibility(View.VISIBLE);
        } else {
            backToTop.setVisibility(View.GONE);
        }
    }
}