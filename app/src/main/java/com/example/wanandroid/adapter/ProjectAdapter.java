package com.example.wanandroid.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wanandroid.R;
import com.example.wanandroid.base.WebActivity;
import com.example.wanandroid.bean.ProjectBean;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;


/**
 * @className: ProjectAdapter
 * @author: Voyager
 * @description: 项目的适配器
 * @date: 2023/6/30
 **/
public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<ProjectBean.DataBean.DatasBean> projectBean = new ArrayList<ProjectBean.DataBean.DatasBean>();

    private ArticleAdapter.BackToTopListener backToTopListener;

    public ArticleAdapter.BackToTopListener getBackToTopListener() {
        return backToTopListener;
    }

    public void setBackToTopListener(ArticleAdapter.BackToTopListener backToTopListener) {
        this.backToTopListener = backToTopListener;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setProjectBean(List<ProjectBean.DataBean.DatasBean> projectBean) {
        this.projectBean = projectBean;

    }

    public ProjectAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

    }

    public ProjectAdapter(List<ProjectBean.DataBean.DatasBean> projectBean) {
        this.projectBean = projectBean;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProjectBean.DataBean.DatasBean datasBean = projectBean.get(position);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_anim);
        holder.itemView.setAnimation(animation);
        holder.title.setHtml(datasBean.getTitle());
        holder.chapterName.setText(datasBean.getChapterName());
        holder.time.setText(datasBean.getNiceDate());
        if (datasBean.getAuthor() != null) {
            holder.author.setText(datasBean.getAuthor());
        } else {
            holder.author.setText(datasBean.getShareUser());
        }
        Glide.with(holder.itemView.getContext())
                .load(datasBean.getEnvelopePic())
                .thumbnail(0.1f)
                .into(holder.imageView);
        holder.title.setOnClickListener(v -> {
            if (datasBean.getLink() != null) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", datasBean.getLink());
                intent.putExtra("tag", 1);//告知WebActivity这是项目，让收藏键不可见
                intent.putExtra("title", datasBean.getTitle());
                holder.title.getContext().startActivity(intent);

            }
        });
        holder.itemView.setOnClickListener(v -> {
            if (datasBean.getLink() != null) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", datasBean.getLink());
                intent.putExtra("title", datasBean.getTitle());
                intent.putExtra("tag", 1);//告知WebActivity这是项目，让收藏键不可见
                holder.title.getContext().startActivity(intent);

            }

        });
        backToTopListener.onBackToTop(holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return projectBean.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView chapterName, author, time;
        HtmlTextView title;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chapterName = itemView.findViewById(R.id.chapterName);
            author = itemView.findViewById(R.id.author);
            time = itemView.findViewById(R.id.time);
            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }


    public interface backToTopListener {
        void backToTop();
    }
}
