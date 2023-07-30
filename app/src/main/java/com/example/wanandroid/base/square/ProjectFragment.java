package com.example.wanandroid.base.square;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.ProjectAdapter;
import com.example.wanandroid.bean.ProjectBean;
import com.example.wanandroid.utils.HttpUtils;
import com.google.android.material.snackbar.Snackbar;
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
 * @className ProjectFragment
 * @description 项目界面
 * @author Voyager
 * @date 2023/7/10 17:04
 */

public class ProjectFragment extends Fragment {

    RecyclerView projectRecyclerView;
    ProjectAdapter projectAdapter;
    SmartRefreshLayout refreshLayout;
    LinearLayout blank_layout,internet_error;
    private LinearLayoutManager manager;
    /**
     * @param data 储存项目数据列表
     * @param loadMoreData 储存加载的项目
     */
    List<ProjectBean.DataBean.DatasBean> data=new ArrayList<>();
    List<ProjectBean.DataBean.DatasBean> loadMoreData=new ArrayList<>();
    /**
     * @param id 分类id
     * @param page 文章获取的页数
     */
    int id,page;
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
        View view=inflater.inflate(R.layout.fragment_project, container, false);
        refreshLayout=view.findViewById(R.id.refresh_layout);
        projectRecyclerView=view.findViewById(R.id.project_recyclerView);
        blank_layout=view.findViewById(R.id.blank_layout);
        internet_error=view.findViewById(R.id.internet_error);
        page=1;
        initData();
        initRefreshLayout();
        initRecyclerView();
        return view;
    }



    private void initData(){
        data.clear();
        Call<ProjectBean> projectBeanCall=HttpUtils.getProjectService().getProjectList(page,id);
        projectBeanCall.enqueue(new Callback<ProjectBean>() {
            @Override
            public void onResponse(@NonNull Call<ProjectBean> call, @NonNull Response<ProjectBean> response) {
                if(response.isSuccessful()){
                    internet_error.setVisibility(View.GONE);
                    ProjectBean projectBean=response.body();
                    if(projectBean!=null&&projectBean.getData()!=null){
                        data=projectBean.getData().getDatas();
                        projectAdapter=new ProjectAdapter(data);
                        requireActivity().runOnUiThread(() -> {
                            blank_layout.setVisibility(View.GONE);
                            initRecyclerView();
                        });


                        //TODO 暂时先弄网络那个吧★★★★
                        //网络显示“网络问题”就再创建一个LinearLayout来显示
                        // ①如果没有网络的话要将page重新置1
                        // ②其次，要判断获取文章response为null时，是否是网络没了还是根本就是数据已经全部显示了（errorCode==0)也许能判断
                        // ③还要把banner的刷新加入刷新和加载


                    }
                    requireActivity().runOnUiThread(() -> {
                        if(data.size()==0){
                            blank_layout.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
            @Override
            public void onFailure(@NonNull Call<ProjectBean> call, Throwable t) {
                Toast.makeText(getContext(),"error:网络问题",Toast.LENGTH_SHORT).show();
                requireActivity().runOnUiThread(() -> {
                    if(data.size()==0&&page==1){
                        internet_error.setVisibility(View.VISIBLE);
                    }else if(data.size()!=0){
                        internet_error.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    /**
     * @methodName loadMoreData
     * @description 获取更多数据
     * @param pageGet 获取的数据所在的页数
     */
    public void loadMoreData(int pageGet){
        loadMoreData.clear();
        Call<ProjectBean> projectBeanCall=HttpUtils.getProjectService().getProjectList(pageGet,id);
        projectBeanCall.enqueue(new Callback<ProjectBean>() {
            @Override
            public void onResponse(@NonNull Call<ProjectBean> call, @NonNull Response<ProjectBean> response) {
                if(response.isSuccessful()){
                    ProjectBean projectBean=response.body();
                    if(projectBean!=null&&projectBean.getData()!=null){
                        loadMoreData.addAll(projectBean.getData().getDatas());
                        if(loadMoreData.isEmpty()){
                            Snackbar.make(refreshLayout,"没有更多数据了",Snackbar.LENGTH_SHORT).show();
                        }
                        data.addAll(projectBean.getData().getDatas());
                        projectAdapter.notifyItemRangeInserted(data.size(),loadMoreData.size());
                        manager.scrollToPositionWithOffset(data.size(), 200);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProjectBean> call, Throwable t) {
                Snackbar.make(refreshLayout,"error:获取数据失败!",Snackbar.LENGTH_SHORT).show();
            }
        });




    }
    private void initRecyclerView(){
        projectAdapter=new ProjectAdapter(data);
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);
        projectAdapter.setContext(getContext());
        projectRecyclerView.setLayoutManager(manager);
        projectRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        projectRecyclerView.setAdapter(projectAdapter);
        projectAdapter.notifyDataSetChanged();
    }
    /**
     * 初始化refreshLayout,添加加载和刷新监听
     */
    private void initRefreshLayout() {
        refreshLayout.setRefreshHeader(new BezierRadarHeader(requireActivity()).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(requireActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(800/*,false*/);//传入false表示刷新失败
                page=1;
                initData();
                projectAdapter.notifyDataSetChanged();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {

                page++;
                loadMoreData(page);
                refreshlayout.finishLoadMore(800);
                refreshlayout.finishLoadMore();

            }
        });
    }
}