package com.example.wanandroid.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.base.WebActivity;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.MessageBean;
import com.example.wanandroid.utils.HttpUtils;
import com.sackcentury.shinebuttonlib.ShineButton;

import net.nightwhistler.htmlspanner.HtmlSpanner;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @className: ArticleAdapter
 * @author: Voyager
 * @description: 文章的通用适配器
 * @date: 2023/6/27
 **/
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<ArticleBean> articleBeanList = new ArrayList<ArticleBean>();
    private Context mContext;
    Activity mActivity;

    private BackToTopListener backToTopListener;

    public int tag=0;


    public void setArticleBeanList(List<ArticleBean> articleBeanList) {
        this.articleBeanList = articleBeanList;

    }

    public ArticleAdapter(List<ArticleBean> articleBeanList, Activity mActivity) {
        this.articleBeanList = articleBeanList;
        this.mActivity = mActivity;

    }

    public BackToTopListener getBackToTopListener() {
        return backToTopListener;
    }

    public void setBackToTopListener(BackToTopListener backToTopListener) {
        this.backToTopListener = backToTopListener;
    }

    /**
     * 是否是收藏的文章
     */
    private int isCollectArticle = 0;



    public void setIsCollectArticle(int isCollectArticle) {
        this.isCollectArticle = isCollectArticle;

    }



    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public ArticleAdapter(List<ArticleBean> articleBeanList) {
        this.articleBeanList = articleBeanList;

    }

    public ArticleAdapter(Context mContext, List<ArticleBean> articleBeanList) {
        this.articleBeanList = articleBeanList;
        this.mContext = mContext;

    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView author, top_text, time, chapterName;
        HtmlTextView title;
        RelativeLayout layout_article;
        ShineButton like;


        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            like = itemView.findViewById(R.id.like);
            author = itemView.findViewById(R.id.author);
            top_text = itemView.findViewById(R.id.top_text);
            title = itemView.findViewById(R.id.title);
            layout_article = itemView.findViewById(R.id.layout_article);
            time = itemView.findViewById(R.id.time);
            chapterName = itemView.findViewById(R.id.chapterName);
        }
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_test, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        ArticleBean articleBean = articleBeanList.get(position);
        if (!articleBean.getAuthor().isEmpty()) {
            holder.author.setText(articleBean.getAuthor());
        } else {
            holder.author.setText(articleBean.getShareUser());
        }
        if (articleBean.getType() == 1) {
            holder.top_text.setVisibility(View.VISIBLE);
        } else {
            holder.top_text.setVisibility(View.GONE);
        }
        holder.like.setChecked(false);
        holder.like.setChecked(articleBean.isCollect());
        holder.like.setOnClickListener(v -> {
            boolean isInner=articleBean.getUrl().contains("wanandroid");
            if(holder.like.isChecked()){
                if(isInner){
                    Call<MessageBean> call= HttpUtils.getwAndroidService().collectInnerArticle(articleBeanList.get(position).getId());
                    call.enqueue(new Callback<MessageBean>() {
                        @Override
                        public void onResponse(@NonNull Call<MessageBean> call, @NonNull Response<MessageBean> response) {
                            if(response.body()==null || response.body().getErrorCode()!=0){
                                Toast.makeText(mContext,"收藏失败",Toast.LENGTH_SHORT).show();
                                holder.like.setChecked(false);
                            }else {
                                Toast.makeText(mContext,"收藏成功",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call<MessageBean> call, @NonNull Throwable t) {
                            Toast.makeText(mContext,"收藏失败",Toast.LENGTH_SHORT).show();
                            holder.like.setChecked(false);
                        }
                    });
                }else {
                    Call<MessageBean> call =HttpUtils.getwAndroidService().collectOutArticle(articleBean.getTitle(), articleBean.getAuthor(), articleBean.getUrl());
                    call.enqueue(new Callback<MessageBean>() {
                        @Override
                        public void onResponse(@NonNull Call<MessageBean> call, @NonNull Response<MessageBean> response) {
                            if(response.body()==null || response.body().getErrorCode()!=0){
                                assert response.body() != null;
                                Toast.makeText(mContext,response.body().getErrorMsg(),Toast.LENGTH_SHORT).show();
                                holder.like.setChecked(false);
                            }else {
                                Toast.makeText(mContext,"收藏成功",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<MessageBean> call, @NonNull Throwable t) {
                            Toast.makeText(mContext,"网络问题",Toast.LENGTH_SHORT).show();
                            holder.like.setChecked(false);
                        }
                    });
                }
            }else {
                if(isCollectArticle==0){
                    HttpUtils.getwAndroidService().uncollectArticleInList(articleBean.getId())
                            .enqueue(new Callback<MessageBean>() {
                        @Override
                        public void onResponse(@NonNull Call<MessageBean> call, @NonNull Response<MessageBean> response) {
                            if(response.body()==null || response.body().getErrorCode()!=0){
                                assert response.body() != null;
                                Toast.makeText(mContext,response.body().getErrorMsg(),Toast.LENGTH_SHORT).show();
                                holder.like.setChecked(true);
                            }else {
                                Toast.makeText(mContext,"取消收藏成功",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<MessageBean> call, @NonNull Throwable t) {
                            Toast.makeText(mContext,"网络问题",Toast.LENGTH_SHORT).show();
                            holder.like.setChecked(true);
                        }
                    });
                }else {
                    HttpUtils.getwAndroidService().unCollectArticleInPerson(articleBean.getId(),articleBean.getOriginId())
                            .enqueue(new Callback<MessageBean>() {
                        @Override
                        public void onResponse(@NonNull Call<MessageBean> call, @NonNull Response<MessageBean> response) {
                            if(response.body()==null || response.body().getErrorCode()!=0){
                                assert response.body() != null;
                                Toast.makeText(mContext,response.body().getErrorMsg(),Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(mContext,"取消收藏成功",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<MessageBean> call, @NonNull Throwable t) {
                            Toast.makeText(mContext,"网络问题",Toast.LENGTH_SHORT).show();
                            holder.like.setChecked(true);
                        }
                    });
                }
            }
        });
        holder.title.setText(new HtmlSpanner().fromHtml(articleBean.getTitle()));
        holder.time.setText(articleBean.getDate());
        if(articleBean.getChapterName().isEmpty()){
            holder.chapterName.setText("站外文章");
        }else {
            holder.chapterName.setText(articleBean.getChapterName());
        }

        holder.title.setOnClickListener(v -> {
            if (articleBean.getUrl() != null) {
                if(articleBean.isCollect()) {
                    isCollectArticle=1;
                }else {
                    isCollectArticle=0;
                }
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("url", articleBean.getUrl());
                intent.putExtra("id", articleBean.getId());
                intent.putExtra("title", articleBean.getTitle());
                intent.putExtra("author", articleBean.getAuthor());
                intent.putExtra("isCollectArticle", isCollectArticle);
                intent.putExtra("originId", articleBean.getOriginId());
                intent.putExtra("shareUser",articleBean.getShareUser());
                intent.putExtra("tag",tag);
                holder.title.getContext().startActivity(intent);

            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (articleBean.getUrl() != null) {
                if(articleBean.isCollect()) {
                    isCollectArticle=1;
                }else {
                    isCollectArticle=0;
                }
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("id", articleBean.getId());
                intent.putExtra("url", articleBean.getUrl());
                intent.putExtra("title", articleBean.getTitle());
                intent.putExtra("author", articleBean.getAuthor());
                intent.putExtra("isCollectArticle", isCollectArticle);
                intent.putExtra("originId", articleBean.getOriginId());
                intent.putExtra("tag",tag);
                holder.itemView.getContext().startActivity(intent);

            }

        });
        //如果不是搜索界面的文章才执行
        if (backToTopListener != null) {
            backToTopListener.onBackToTop(holder.getAdapterPosition());
        }


    }





    @Override
    public int getItemCount() {
        return articleBeanList != null ? articleBeanList.size() : 0;
    }

    public interface BackToTopListener {

        void onBackToTop(int position);

    }
}
