package com.example.wanandroid.base.square;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.ProjectCategoryAdapter;
import com.example.wanandroid.bean.ProjectCategoryBean;
import com.example.wanandroid.utils.HttpUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @author Voyager
 * @className SquareFragment
 * @description 发现界面，显示界面
 * @createTime
 */

public class SquareFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    SmartRefreshLayout refreshLayout;
    LinearLayout internet_error;
    FloatingActionButton refresh_button;

    public SquareFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_square, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.viewPager_container);
        internet_error = view.findViewById(R.id.internet_error);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        refresh_button = view.findViewById(R.id.refresh_button);

        initData();
        initView();
        return view;
    }

    /**
     * 初始化视图
     */
    private void initView() {
        refresh_button.setOnClickListener(v -> {
            initData();
        });
    }

    /**
     * 初始化chapterName数据
     */
    private void initData() {

        Call<ProjectCategoryBean> projectCategoryBeanCall = HttpUtils.getProjectService().getProjectCategory();
        projectCategoryBeanCall.enqueue(new Callback<ProjectCategoryBean>() {
            @Override
            public void onResponse(@NonNull Call<ProjectCategoryBean> call, @NonNull Response<ProjectCategoryBean> response) {
                if (response.isSuccessful()) {
                    ProjectCategoryBean projectCategoryBean = response.body();
                    if (projectCategoryBean != null) {
                        List<ProjectCategoryBean.DataBean> data = projectCategoryBean.getData();
                        List<String> titles = new ArrayList<>();
                        List<ProjectFragment> fragments = new ArrayList<>();
                        if (data != null && data.size() > 0) {
                            for (int i = 0; i < data.size(); i++) {
                                ProjectCategoryBean.DataBean dataBean = data.get(i);
                                titles.add(dataBean.getName());
                                ProjectFragment projectFragment = new ProjectFragment(dataBean.getId());
                                fragments.add(projectFragment);

                            }

                            requireActivity().runOnUiThread(() -> {

                                viewPager.setAdapter(new ProjectCategoryAdapter(getChildFragmentManager(), fragments, titles));
                                tabLayout.setupWithViewPager(viewPager);
                                internet_error.setVisibility(View.GONE);
                                refresh_button.setVisibility(View.GONE);

                            });

                        } else {
                            requireActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    refresh_button.setVisibility(View.VISIBLE);
                                    internet_error.setVisibility(View.VISIBLE);
                                }
                            });
                        }

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProjectCategoryBean> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error：网络问题", Toast.LENGTH_SHORT).show();
                refresh_button.setVisibility(View.VISIBLE);
                internet_error.setVisibility(View.VISIBLE);
            }
        });


    }


}