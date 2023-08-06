package com.example.wanandroid.base.person;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.WebsiteAdapter;
import com.example.wanandroid.bean.WebsiteBean;
import com.example.wanandroid.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebsiteActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton cancel;
    WebsiteAdapter websiteAdapter;
    private List<WebsiteBean.DataBean> dataBeanList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
        recyclerView=findViewById(R.id.websiteRecyclerView);
        cancel=findViewById(R.id.cancel);
        initRecyclerView();
        getWebsiteData();

        cancel.setOnClickListener(v -> {
            finish();
        });

    }



    private void initRecyclerView() {
        websiteAdapter=new WebsiteAdapter(dataBeanList);
        websiteAdapter.setmContext(this);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(websiteAdapter);
    }


    private void getWebsiteData(){
        Call<WebsiteBean> call= HttpUtils.getwAndroidService().getWebsiteData();
        call.enqueue(new Callback<WebsiteBean>() {
            @Override
            public void onResponse(@NonNull Call<WebsiteBean> call, @NonNull Response<WebsiteBean> response) {
                if(response.isSuccessful()){
                    WebsiteBean websiteBean=response.body();
                    dataBeanList=websiteBean.getData();
                    websiteAdapter.setDataBeanList(dataBeanList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            websiteAdapter.notifyDataSetChanged();
                        }
                    });

                }
            }

            @Override
            public void onFailure(@NonNull Call<WebsiteBean> call, @NonNull Throwable t) {
                Toast.makeText(WebsiteActivity.this,"网络问题",Toast.LENGTH_SHORT).show();
            }
        });
    }


}