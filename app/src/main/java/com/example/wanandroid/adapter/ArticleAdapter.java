package com.example.wanandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.base.WebActivity;
import com.example.wanandroid.bean.ArticleBean;

import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.List;

/**
 * @className: ArticleAdapter
 * @author: Voyager
 * @description:
 * @date: 2023/6/27
 **/
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<ArticleBean> articleBeanList;
    private OnLoadMoreListener onLoadMoreListener;
    private Context mContext;

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public ArticleAdapter(List<ArticleBean> articleBeanList) {
        this.articleBeanList = articleBeanList;
    }



    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.onLoadMoreListener = listener;
    }
    public static class ArticleViewHolder extends RecyclerView.ViewHolder{
            TextView author,top_text,title,time,chapterName;
            ShineButton like;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            top_text = itemView.findViewById(R.id.top_text);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
            chapterName = itemView.findViewById(R.id.chapterName);
            like = itemView.findViewById(R.id.like);

        }
    }
    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article,parent,false);
        ArticleViewHolder holder=new ArticleViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        ArticleBean articleBean = articleBeanList.get(position);
        holder.author.setText(articleBean.getAuthor());
        if(articleBean.getType()==1) {
            holder.top_text.setVisibility(View.VISIBLE);
        }
        holder.title.setText(articleBean.getTitle());
        holder.time.setText(articleBean.getDate());
        holder.chapterName.setText(articleBean.getChapterName());
        if (position == getItemCount() - 1) {
            if (onLoadMoreListener != null) {
                onLoadMoreListener.onLoadMore();
            }
        }
        holder.like.setOnClickListener(v -> {

        });
        holder.itemView.setOnClickListener(v -> {
            if(articleBean.getUrl() != null) {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("url",articleBean.getUrl());
                holder.itemView.getContext().startActivity(intent);

            }

        });
    }



    @Override
    public int getItemCount() {
        //TODO 懒加载，我的想法是：由于文章的获取是按page获取的，因此可以下拉刷新一次就get一次数据（page++），然后add数据之后UI刷新
        return articleBeanList.size();
    }
    public void refreshData(){
        notifyDataSetChanged();
    }
}
