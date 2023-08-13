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
    List<CommentBean.DataBean.DatasBean> datasBeanList = new ArrayList<CommentBean.DataBean.DatasBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
        initRecyclerView();
        getUnReadMessageData(0);
    }

    private void initView() {
        messageRecyclerView = findViewById(R.id.messageRecyclerView);
        cancel = findViewById(R.id.cancel);
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
                    datasBeanList.addAll(commentBean.getData().getDatas());
                    if (!datasBeanList.isEmpty()) {
                        commentAdapter.setDatasBeanList(datasBeanList);

                        commentAdapter.notifyDataSetChanged();
                        getUnReadMessageData(pageGet + 1);

                    } else {
                        getReadMessageData(0);
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<CommentBean> call, @NonNull Throwable t) {
                Toast.makeText(MessageActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
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
                    commentAdapter.setDatasBeanList(datasBeanList);
                    commentAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommentBean> call, @NonNull Throwable t) {

            }
        });
    }


}