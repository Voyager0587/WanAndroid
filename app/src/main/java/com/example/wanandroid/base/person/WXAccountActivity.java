package com.example.wanandroid.base.person;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.WXAccountAdapter;
import com.example.wanandroid.bean.WXArticleChapterBean;
import com.example.wanandroid.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @className WXAccountActivity
 * @description 微信公众号列表显示
 * @author Voyager
 * @date 2023/8/14 20:40
 */

public class WXAccountActivity extends AppCompatActivity {
    
    List<WXArticleChapterBean.DataBean> wxArticleChapterList=new ArrayList<WXArticleChapterBean.DataBean>();
    RecyclerView recyclerView;
    WXAccountAdapter adapter;
    ImageButton cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxaccount);
        recyclerView=findViewById(R.id.wxAccountRecyclerView);
        cancel=findViewById(R.id.cancel);
        initRecyclerView();
        getWXAccounts();
        initListener();

    }

    private void initListener(){
        cancel.setOnClickListener(v -> {
            finish();
        });
    }
    private void initRecyclerView(){
        adapter=new WXAccountAdapter(wxArticleChapterList,this,WXAccountActivity.this);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        
    }


    private void getWXAccounts(){
        Call<WXArticleChapterBean> call= HttpUtils.getwAndroidService().getWXArticleChapters();
        call.enqueue(new Callback<WXArticleChapterBean>() {
            @Override
            public void onResponse(@NonNull Call<WXArticleChapterBean> call, @NonNull Response<WXArticleChapterBean> response) {
                if(response.body()!=null){
                    WXArticleChapterBean chapterBean=response.body();
                    wxArticleChapterList.addAll(chapterBean.getData());
                    adapter.setWxArticleChapterList(wxArticleChapterList);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();;
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<WXArticleChapterBean> call, @NonNull Throwable t) {
                Toast.makeText(WXAccountActivity.this,"网络问题",Toast.LENGTH_SHORT).show();
            }
        });
    }

}