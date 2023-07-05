package com.example.wanandroid.adapter;

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
 * @className: ChapterAdapter
 * @author: Voyager
 * @description:
 * @date: 2023/7/5
 **/
public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder>{

    List<ChapterBean.DataBean.ChildrenBean> childrenBeanList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    public ChapterAdapter(List<ChapterBean.DataBean.ChildrenBean> childrenBeanList) {
        this.childrenBeanList = childrenBeanList;
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter_name,parent,false);
        ChapterViewHolder holder=new ChapterViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        holder.textView.setText(childrenBeanList.get(position).getName());
        holder.textView.setOnClickListener(v -> {
            onItemClickListener.onItemClick(v, holder.getAdapterPosition());
        });
    }


    @Override
    public int getItemCount() {
        return childrenBeanList.size();
    }

    public static class ChapterViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.chapterName);
        }
    }
}
