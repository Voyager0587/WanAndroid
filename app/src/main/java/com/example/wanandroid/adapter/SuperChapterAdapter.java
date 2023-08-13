package com.example.wanandroid.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.ChapterBean;

import java.util.List;

/**
 * @className: SuperChapterAdapter
 * @author: Voyager
 * @description: 一级分类标题 SuperChapterName的适配器
 * @date: 2023/7/4
 **/
public class SuperChapterAdapter extends RecyclerView.Adapter<SuperChapterAdapter.ChapterViewHolder> {
    List<ChapterBean.DataBean> data;
    private OnSuperItemClickListener onItemClickListener;
    private int selectedPosition = 0;

    public void setOnItemClickListener(OnSuperItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SuperChapterAdapter(List<ChapterBean.DataBean> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter_name, parent, false);
        ChapterViewHolder holder = new ChapterViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        holder.textView.setText(data.get(position).getName());
        if (selectedPosition == holder.getAdapterPosition()) {
            holder.itemView.setBackgroundResource(holder.bgBlue);
            holder.textView.setTextColor(Color.parseColor("#1194AA"));
        } else {
            holder.itemView.setBackgroundResource(holder.bgWhite);
            holder.textView.setTextColor(Color.parseColor("#FF000000"));
        }
//        holder.itemView.setBackgroundColor(selectedPosition == holder.getAdapterPosition() ? holder.bgBlue : holder.bgWhite);
        holder.textView.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            onItemClickListener.onSuperItemClick(v, holder.getAdapterPosition());

        });

        //想一想要不要设计两个Adapter
        //初始化列表时先加入“全部文章”这个分类

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ChapterViewHolder extends RecyclerView.ViewHolder {
        int bgBlue, bgWhite;
        TextView textView;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.chapterName);
            bgWhite = R.drawable.item_chapter2;
            bgBlue = R.drawable.item_chapter_selected;
        }
    }

    public interface OnSuperItemClickListener {
        void onSuperItemClick(View view, int position);
    }

}
