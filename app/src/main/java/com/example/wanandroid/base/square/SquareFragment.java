package com.example.wanandroid.base.square;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.ProjectCategoryAdapter;
import com.example.wanandroid.bean.ProjectCategoryBean;
import com.example.wanandroid.utils.HttpUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @className SquareFragment
 * @description 发现界面，显示界面
 * @author Voyager
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
        View view=inflater.inflate(R.layout.fragment_square, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.viewPager_container);
        internet_error=view.findViewById(R.id.internet_error);
        refreshLayout=view.findViewById(R.id.refresh_layout);
        refresh_button=view.findViewById(R.id.refresh_button);
        initData();
        initView();
        return view;
    }

    /**
     * 初始化视图
     */
    private void initView() {
//        refreshLayout.setRefreshHeader(new BezierRadarHeader(requireActivity()).setEnableHorizontalDrag(true));
//        //设置 Footer 为 球脉冲 样式
//        refreshLayout.setRefreshFooter(new BallPulseFooter(requireActivity()).setSpinnerStyle(SpinnerStyle.Scale));
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                refreshLayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
//
//                //FIXME 下拉刷新有两个--嘶
//            }
//        });

        refresh_button.setOnClickListener(v -> {
            initData();
        });
    }

    /**
     * 初始化chapterName数据
     */
    private void initData() {

        Call<ProjectCategoryBean> projectCategoryBeanCall= HttpUtils.getProjectService().getProjectCategory();
        projectCategoryBeanCall.enqueue(new Callback<ProjectCategoryBean>() {
            @Override
            public void onResponse(@NonNull Call<ProjectCategoryBean> call, @NonNull Response<ProjectCategoryBean> response) {
                if(response.isSuccessful()){
                    ProjectCategoryBean projectCategoryBean=response.body();
                    if(projectCategoryBean!=null){
                        List<ProjectCategoryBean.DataBean> data=projectCategoryBean.getData();
                        List<String> titles=new ArrayList<>();
                        List<ProjectFragment> fragments=new ArrayList<>();
                        if(data!=null&&data.size()>0){
                            requireActivity().runOnUiThread(() -> {
                                for(int i=0;i<data.size();i++){
                                    ProjectCategoryBean.DataBean dataBean=data.get(i);
                                    titles.add(dataBean.getName());
                                    ProjectFragment projectFragment=new ProjectFragment(dataBean.getId());
                                    fragments.add(projectFragment);

                                }
                                viewPager.setAdapter(new ProjectCategoryAdapter(getChildFragmentManager(),fragments,titles));
                                tabLayout.setupWithViewPager(viewPager);
                                internet_error.setVisibility(View.GONE);
                                refresh_button.setVisibility(View.GONE);
                                //FIXME 进入发现界面前断网不会显示网络问题；
                            });

                        }else {
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

            }
        });



    }






}