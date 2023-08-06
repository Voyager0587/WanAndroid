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

import com.bumptech.glide.Glide;
import com.example.wanandroid.R;
import com.example.wanandroid.base.WebActivity;
import com.example.wanandroid.base.person.WebsiteActivity;
import com.example.wanandroid.bean.WebsiteBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @className: WebsiteAdapter
 * @author: Voyager
 * @description: 网站的适配器
 * @date: 2023/8/6 17:20
 **/
public class WebsiteAdapter extends RecyclerView.Adapter<WebsiteAdapter.WebsiteViewHolder> {

    private List<WebsiteBean.DataBean> dataBeanList=new ArrayList<>();
    private Context mContext;

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setDataBeanList(List<WebsiteBean.DataBean> dataBeanList) {
        this.dataBeanList = dataBeanList;
    }

    public WebsiteAdapter(List<WebsiteBean.DataBean> dataBeanList) {
        this.dataBeanList = dataBeanList;
    }

    @NonNull
    @Override
    public WebsiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_website,parent,false);
        return new WebsiteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WebsiteViewHolder holder, int position) {
        holder.website_name.setText(dataBeanList.get(position).getName());
        String originalPath=dataBeanList.get(position).getLink();
        String imagePath="";
        int first=originalPath.indexOf("/");
        int second=originalPath.indexOf("/",first+1);
        int a=originalPath.indexOf("/",second+1);
        if(a==-1) {
            imagePath=originalPath+"/favicon.ico";
        }else {
            imagePath=originalPath.substring(0,a+1)+"favicon.ico";
        }
        imagePath=imagePath.replace("http://","https://");
        Glide.with(holder.imageView)
                .load(imagePath)
                .thumbnail(Glide.with(holder.itemView).load(R.drawable.blogger))
                .into(holder.imageView);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, WebActivity.class);
            intent.putExtra("url",dataBeanList.get(position).getLink().replace("http://","https://"));
            intent.putExtra("tag",1);
            mContext.startActivity(intent);
        });
    }

    //TODO 加一个消息列表
    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    static class WebsiteViewHolder extends RecyclerView.ViewHolder{

        TextView website_name;
        ImageView imageView;
        public WebsiteViewHolder(@NonNull View itemView) {
            super(itemView);
            website_name=itemView.findViewById(R.id.website_name);
            imageView=itemView.findViewById(R.id.website_icon);
        }
    }

}
