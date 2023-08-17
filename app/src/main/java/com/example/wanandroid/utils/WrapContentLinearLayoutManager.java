package com.example.wanandroid.utils;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @version 1.0
 * @className: WrapContentLinearLayoutManager
 * @author: Voyager
 * @description: 为了解决多次刷新后点击二级标题报错：java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid view holder adapter positionArticleViewHolder
 * @date: 2023/8/17 15:40
 **/
public class WrapContentLinearLayoutManager extends LinearLayoutManager {

    public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e("TAG", "meet a IOOBE in RecyclerView");
        }
    }
}