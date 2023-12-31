package com.example.wanandroid.base.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.HotkeyAdapter;
import com.example.wanandroid.bean.HotkeyBean;
import com.example.wanandroid.utils.HttpUtils;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @author Voyager
 * @className HotkeyFragment
 * @description 显示热词的界面
 * @date
 */

public class HotkeyFragment extends Fragment implements HotkeyAdapter.OnListener {
    RecyclerView hotkey_recyclerView;
    FlexboxLayoutManager flexboxLayoutManager;
    LinearLayout refresh_layout;
    List<HotkeyBean.DataBean> dataBeanList = new ArrayList<>();
    HotkeyAdapter hotkeyAdapter;


    public HotkeyFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotkey, container, false);
        hotkey_recyclerView = view.findViewById(R.id.hotkey_recyclerView);
        refresh_layout = view.findViewById(R.id.refresh_layout);


        flexboxLayoutManager = new FlexboxLayoutManager(requireActivity());
        hotkey_recyclerView.setLayoutManager(flexboxLayoutManager);
        hotkey_recyclerView.setHasFixedSize(true);
        hotkey_recyclerView.setNestedScrollingEnabled(false);
        //TODO 拓展：添加搜索记录功能
        initRecyclerView();
        getHotkeyData();
        initListener();
        return view;
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        refresh_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().runOnUiThread(() -> {
                    getHotkeyData();
                });
            }
        });
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        hotkeyAdapter = new HotkeyAdapter(dataBeanList);
        hotkey_recyclerView.setAdapter(hotkeyAdapter);
        hotkeyAdapter.setmListener(this);
        hotkeyAdapter.notifyDataSetChanged();

    }

    /**
     * 获取搜索热词
     */
    private void getHotkeyData() {

        Call<HotkeyBean> call = HttpUtils.getwAndroidService().getHotkeyData();
        call.enqueue(new Callback<HotkeyBean>() {
            @Override
            public void onResponse(@NonNull Call<HotkeyBean> call, @NonNull Response<HotkeyBean> response) {
                if (response.isSuccessful()) {
                    HotkeyBean hotkeyBean = response.body();
                    if (hotkeyBean != null) {
                        dataBeanList.clear();
                        dataBeanList = hotkeyBean.getData();
                        requireActivity().runOnUiThread(() -> {
                            hotkeyAdapter = new HotkeyAdapter(dataBeanList);
                            hotkey_recyclerView.setAdapter(hotkeyAdapter);
                            hotkeyAdapter.setmListener(HotkeyFragment.this);
                            hotkeyAdapter.notifyDataSetChanged();
                        });
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<HotkeyBean> call, @NonNull Throwable t) {
                Snackbar.make(refresh_layout, "获取热词失败！", Snackbar.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * RecyclerView的item点击事件
     *
     * @param view
     * @param position 点击的item位置
     */
    @Override
    public void onItemClick(View view, int position) {
        TextView textView = view.findViewById(R.id.hotkey);
        String input = textView.getText().toString().trim();
        ((SearchActivity) requireActivity()).onTextClicked(input);
    }
}