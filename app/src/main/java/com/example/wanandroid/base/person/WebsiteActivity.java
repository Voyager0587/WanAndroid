package com.example.wanandroid.base.person;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.WebsiteAdapter;
import com.example.wanandroid.bean.WebsiteBean;
import com.example.wanandroid.utils.HttpUtils;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebsiteActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton cancel;
    WebsiteAdapter websiteAdapter;
    SmartRefreshLayout refresh_layout;
    private List<WebsiteBean.DataBean> dataBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
        recyclerView = findViewById(R.id.websiteRecyclerView);
        cancel = findViewById(R.id.cancel);
        refresh_layout = findViewById(R.id.refresh_layout);
        initRecyclerView();
        getWebsiteData();
        initRefreshLayout();
        cancel.setOnClickListener(v -> {
            finish();
        });

    }

    private void initRefreshLayout() {
        refresh_layout.setRefreshHeader(new BezierRadarHeader(WebsiteActivity.this).setEnableHorizontalDrag(true));
        refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                getWebsiteData();
            }
        });

    }


    private void initRecyclerView() {
        websiteAdapter = new WebsiteAdapter(dataBeanList);
        websiteAdapter.setmContext(this);
        websiteAdapter.setDataBeanList(dataBeanList);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(websiteAdapter);
    }


    private void getWebsiteData() {
        Call<WebsiteBean> call = HttpUtils.getwAndroidService().getWebsiteData();
        call.enqueue(new Callback<WebsiteBean>() {
            @Override
            public void onResponse(@NonNull Call<WebsiteBean> call, @NonNull Response<WebsiteBean> response) {
                if (response.body()!=null) {
                    dataBeanList.clear();
                    WebsiteBean websiteBean = response.body();
                    dataBeanList.addAll(websiteBean.getData());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            websiteAdapter.notifyDataSetChanged();
                            refresh_layout.finishRefresh();
                        }
                    });

                }
            }

            @Override
            public void onFailure(@NonNull Call<WebsiteBean> call, @NonNull Throwable t) {
                Toast.makeText(WebsiteActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
                refresh_layout.finishRefresh();
            }
        });
    }


}