package com.example.wanandroid.base.person;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.example.wanandroid.R;
import com.example.wanandroid.base.CollectArticleActivity;
import com.google.android.material.snackbar.Snackbar;


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
        //TODO 信息界面里面还可以加入软件分享链接或者二维码
        initListener();
        return view;

    }
    //TODO 个人信息编辑界面的进入，就通过右上角，放一个"person_icon"
    private void initListener() {

        collectArticle.setOnClickListener(v -> {
            Snackbar.make(collectArticle,"进入收藏文章界面", Snackbar.LENGTH_SHORT).show();
            Intent intent = new Intent(requireActivity(), CollectArticleActivity.class);
            requireActivity().startActivity(intent);
        });

        info_layout.setOnClickListener(v -> {

        });

        logout_layout.setOnClickListener(v -> {

        });

    }
}