package com.example.wanandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.base.WebActivity;
import com.example.wanandroid.base.person.MessageActivity;
import com.example.wanandroid.bean.CommentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @className: CommentAdapter
 * @author: Voyager
 * @description: 评论的适配器
 * @date: 2023/8/9
 **/
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    List<CommentBean.DataBean.DatasBean> datasBeanList = new ArrayList<CommentBean.DataBean.DatasBean>();
    private Context mContext;

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setDatasBeanList(List<CommentBean.DataBean.DatasBean> datasBeanList) {
        this.datasBeanList = datasBeanList;
    }

    public CommentAdapter(List<CommentBean.DataBean.DatasBean> datasBeanList) {
        this.datasBeanList = datasBeanList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentBean.DataBean.DatasBean datasBean = datasBeanList.get(position);
        holder.title.setText(datasBean.getTitle());
        holder.message.setText(datasBean.getMessage());
        holder.time.setText(datasBean.getNiceDate());
        holder.name.setText(datasBean.getFromUser());
        if(datasBean.getIsRead()==0){
            holder.tag.setVisibility(View.VISIBLE);
        }else {
            holder.tag.setVisibility(View.INVISIBLE);
        }
        Intent intent = new Intent(mContext, WebActivity.class);
        intent.putExtra("id", datasBean.getId());
        intent.putExtra("url",datasBean.getFullLink());
        holder.itemView.setOnClickListener(v -> {
            mContext.startActivity(intent);
        });

        holder.message.setOnClickListener(v -> {
            mContext.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return datasBeanList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView title, message, time,name;
        ImageView tag;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
            tag = itemView.findViewById(R.id.tag);
            name = itemView.findViewById(R.id.name);
        }
    }

}
