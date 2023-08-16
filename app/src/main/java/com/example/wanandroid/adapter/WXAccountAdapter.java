package com.example.wanandroid.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.base.person.WXArticleActivity;
import com.example.wanandroid.bean.WXArticleChapterBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @className: WXAccountAdapter
 * @author: Voyager
 * @description: 微信公众号列表的适配器
 * @date: 2023/8/15 21:46
 **/
public class WXAccountAdapter extends RecyclerView.Adapter<WXAccountAdapter.WXAccountViewHolder>{

    List<WXArticleChapterBean.DataBean> wxArticleChapterList=new ArrayList<WXArticleChapterBean.DataBean>();
    private Context mContext;
    Activity activity;

    public WXAccountAdapter(List<WXArticleChapterBean.DataBean> wxArticleChapterList, Context mContext, Activity activity) {
        this.wxArticleChapterList = wxArticleChapterList;
        this.mContext = mContext;
        this.activity = activity;
    }

    public WXAccountAdapter(List<WXArticleChapterBean.DataBean> wxArticleChapterList) {
        this.wxArticleChapterList = wxArticleChapterList;
    }

    public WXAccountAdapter(List<WXArticleChapterBean.DataBean> wxArticleChapterList, Context mContext) {
        this.wxArticleChapterList = wxArticleChapterList;
        this.mContext = mContext;
    }

    public void setWxArticleChapterList(List<WXArticleChapterBean.DataBean> wxArticleChapterList) {
        this.wxArticleChapterList = wxArticleChapterList;
    }

    @NonNull
    @Override
    public WXAccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wx_acount,parent,false);
        return new WXAccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WXAccountViewHolder holder, int position) {
        holder.wx_name.setText(wxArticleChapterList.get(position).getName());
        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(mContext, WXArticleActivity.class);
            intent.putExtra("id",wxArticleChapterList.get(position).getId());
            intent.putExtra("name",wxArticleChapterList.get(position).getName());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return wxArticleChapterList.size();
    }

    static class WXAccountViewHolder extends RecyclerView.ViewHolder{
        TextView wx_name;
        public WXAccountViewHolder(@NonNull View itemView) {
            super(itemView);
            wx_name=itemView.findViewById(R.id.wx_name);
        }
    }
}
