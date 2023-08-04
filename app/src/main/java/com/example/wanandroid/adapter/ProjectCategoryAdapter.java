package com.example.wanandroid.adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.wanandroid.base.square.ProjectFragment;

import java.util.List;

/**
 * @className: ProjectCategoryAdapter
 * @author: Voyager
 * @description: 项目分裂的适配器
 * @date: 2023/6/30
 **/
public class ProjectCategoryAdapter extends FragmentPagerAdapter {
    private List<ProjectFragment> fragmentList;
    private List<String> titles;

    public ProjectCategoryAdapter(FragmentManager manager, List<ProjectFragment> fragments, List<String> titles) {
        super(manager);
        this.fragmentList = fragments;
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList != null ? fragmentList.size() : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

}
