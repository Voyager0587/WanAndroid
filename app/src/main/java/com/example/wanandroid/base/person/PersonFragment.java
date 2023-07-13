package com.example.wanandroid.base.person;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.MessageBean;
import com.example.wanandroid.utils.HttpUtils;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @className PersonFragment
 * @description 个人界面
 * @author Voyager
 * @createTime
 */

public class PersonFragment extends Fragment {
    RelativeLayout collectArticle,info_layout,logout_layout;

    public PersonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_person, container, false);
        collectArticle=view.findViewById(R.id.collectArticle);
        info_layout=view.findViewById(R.id.info_layout);
        logout_layout=view.findViewById(R.id.logout_layout);
        //TODO 拓展：信息界面里面还可以加入软件分享链接或者二维码
        initListener();
        return view;

    }
    //TODO 个人信息编辑界面的进入，就通过右上角，放一个"person_icon"★★
    private void initListener() {

        collectArticle.setOnClickListener(v -> {

            Intent intent = new Intent(requireActivity(), CollectArticleActivity.class);
            requireActivity().startActivity(intent);
        });

        info_layout.setOnClickListener(v -> {

        });

        logout_layout.setOnClickListener(v -> {
            Call<MessageBean> logout= HttpUtils.getUserService().logout();
            logout.enqueue(new Callback<MessageBean>() {
                @Override
                public void onResponse(@NonNull Call<MessageBean> call, @NonNull Response<MessageBean> response) {
                    if (response.isSuccessful()){
                        //TODO 看看返回信息是否是注销成功
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MessageBean> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}