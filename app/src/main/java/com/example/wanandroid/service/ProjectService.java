package com.example.wanandroid.service;

import com.example.wanandroid.bean.ProjectBean;
import com.example.wanandroid.bean.ProjectCategoryBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @className: ProjectService
 * @author: Voyager
 * @description:
 * @date: 2023/6/30
 **/
public interface ProjectService {

    /**
     * 获取项目分类列表
     * @return ProjectCategoryBean
     */
    @GET("project/tree/json")
    Call<ProjectCategoryBean> getProjectCategory();


    /**
     * 获取项目某一分类di下的项目
     * @param page 页数
     * @param cid 分类id
     * @return ProjectBean
     */
    @GET("project/list/{page}/json")
    Call<ProjectBean> getProjectList(@Path("page") int page, @Query("cid") int cid);


}
