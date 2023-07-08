package com.example.wanandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.base.WebActivity;
import com.example.wanandroid.bean.ArticleBean;

import com.google.android.material.snackbar.Snackbar;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import org.sufficientlysecure.htmltextview.ClickableTableSpan;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

/**
 * @className: ArticleAdapter
 * @author: Voyager
 * @description:
 * @date: 2023/6/27
 **/
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<ArticleBean> articleBeanList;
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

    public ArticleAdapter( Context mContext,List<ArticleBean> articleBeanList) {
        this.articleBeanList = articleBeanList;
        this.mContext = mContext;
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder{
            TextView author,top_text,time,chapterName;
            HtmlTextView title;
         RelativeLayout layout_article;
        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            top_text = itemView.findViewById(R.id.top_text);
            title = itemView.findViewById(R.id.title);
            layout_article=itemView.findViewById(R.id.layout_article);
            time = itemView.findViewById(R.id.time);
            chapterName = itemView.findViewById(R.id.chapterName);


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
        int type = articleBean.getType();
        if(articleBean.getType()==1) {
            holder.top_text.setVisibility(View.VISIBLE);
        }else {
            holder.top_text.setVisibility(View.GONE);
        }
        holder.title.setHtml(articleBean.getTitle());
        holder.time.setText(articleBean.getDate());
        holder.chapterName.setText(articleBean.getChapterName());


        holder.title.setOnClickListener(v -> {
            if(articleBean.getUrl() != null) {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("url",articleBean.getUrl());
                intent.putExtra("id",articleBean.getId());
                intent.putExtra("title",articleBean.getTitle());
                intent.putExtra("author",articleBean.getAuthor());
                holder.itemView.getContext().startActivity(intent);

            }
        });
        //TODO 点赞那个先别弄了，实在不行就把收藏写在AgentWebView里面，就在X旁边或者菜单里面★★★
        holder.itemView.setOnClickListener(v -> {
            if(articleBean.getUrl() != null) {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("id",articleBean.getId());
                intent.putExtra("url",articleBean.getUrl());
                holder.itemView.getContext().startActivity(intent);

            }

        });
    }



    @Override
    public int getItemCount() {

        return articleBeanList.size();
    }
    public void refreshData(){
        notifyDataSetChanged();
    }
}
