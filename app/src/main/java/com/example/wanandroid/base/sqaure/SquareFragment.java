package com.example.wanandroid.base.sqaure;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.ProjectCategoryAdapter;
import com.example.wanandroid.bean.ProjectCategoryBean;
import com.example.wanandroid.utils.HttpUtils;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @author liukai
 */
public class SquareFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    SmartRefreshLayout refreshLayout;
    public SquareFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_square, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.viewPager_container);
        initData();
        return view;
    }

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