package com.example.wanandroid.base.person;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.CommentAdapter;
import com.example.wanandroid.bean.CommentBean;
import com.example.wanandroid.utils.HttpUtils;
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
 * @author Voyager
 * @className MessageActivity
 * @description 消息列表
 * @date 2023/8/9
 */
public class MessageActivity extends AppCompatActivity {

    RecyclerView messageRecyclerView;
    ImageButton cancel;
    CommentAdapter commentAdapter;
    SmartRefreshLayout refresh_layout;
    List<CommentBean.DataBean.DatasBean> datasBeanList = new ArrayList<CommentBean.DataBean.DatasBean>();
    int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
        initRecyclerView();
        initRefreshLayout();
        getUnReadMessageData(1);
    }


    private void initRefreshLayout(){
        refresh_layout.setRefreshHeader(new BezierRadarHeader(MessageActivity.this).setEnableHorizontalDrag(true));
        refresh_layout.setRefreshFooter(new BallPulseFooter(MessageActivity.this).setSpinnerStyle(SpinnerStyle.Scale));
        refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                datasBeanList.clear();
                page=1;
                getUnReadMessageData(1);
            }
        });
        refresh_layout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getReadMessageData(page);
            }
        });
    }


    private void initView() {
        messageRecyclerView = findViewById(R.id.messageRecyclerView);
        cancel = findViewById(R.id.cancel);
        refresh_layout = findViewById(R.id.refresh_layout);
        cancel.setOnClickListener(v -> {
            finish();
        });
    }

    private void initRecyclerView() {
        commentAdapter = new CommentAdapter(datasBeanList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        commentAdapter.setmContext(this);
        messageRecyclerView.setLayoutManager(manager);
        messageRecyclerView.setAdapter(commentAdapter);
    }

    /**
     * 获取未读的消息列表
     *
     * @param pageGet 页数
     */
    private void getUnReadMessageData(int pageGet) {
        Call<CommentBean> call = HttpUtils.getwAndroidService().getUnreadComments(pageGet);
        call.enqueue(new Callback<CommentBean>() {
            @Override
            public void onResponse(@NonNull Call<CommentBean> call, @NonNull Response<CommentBean> response) {
                if (response.isSuccessful()) {
                    CommentBean commentBean = response.body();
                    assert commentBean != null;
                    datasBeanList.addAll(commentBean.getData().getDatas());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!commentBean.getData().getDatas().isEmpty()) {
                                getUnReadMessageData(pageGet + 1);
                            } else {
                                getReadMessageData(1);
                            }
                        }
                    });


                }
            }

            @Override
            public void onFailure(@NonNull Call<CommentBean> call, @NonNull Throwable t) {
                Toast.makeText(MessageActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
                refresh_layout.finishRefresh();
            }

        });

    }

    /**
     * 获取已读消息列表
     *
     * @param pageGet 页数
     */
    private void getReadMessageData(int pageGet) {
        Call<CommentBean> call = HttpUtils.getwAndroidService().getReadComments(pageGet);
        call.enqueue(new Callback<CommentBean>() {
            @Override
            public void onResponse(@NonNull Call<CommentBean> call, @NonNull Response<CommentBean> response) {
                if (response.isSuccessful()) {
                    CommentBean commentBean = response.body();
                    assert commentBean != null;
                    datasBeanList.addAll(commentBean.getData().getDatas());
                    runOnUiThread(()->{
                        commentAdapter.notifyDataSetChanged();
                        if(pageGet==1){
                            refresh_layout.finishRefresh();
                        }else {
                            if(commentBean.getData().getDatas().isEmpty()) {
                                Toast.makeText(MessageActivity.this,"没有更多数据了",Toast.LENGTH_SHORT).show();
                            }
                            refresh_layout.finishLoadMore();
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommentBean> call, @NonNull Throwable t) {
                Toast.makeText(MessageActivity.this,"网络问题",Toast.LENGTH_SHORT).show();
                if(pageGet>1){
                    page--;
                }
                if(pageGet==1){
                    refresh_layout.finishRefresh();
                }else {
                    refresh_layout.finishLoadMore();
                }
            }
        });
    }


}