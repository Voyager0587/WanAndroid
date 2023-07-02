package com.example.wanandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wanandroid.R;
import com.example.wanandroid.bean.BannerBean;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * @className: BannerAdapter
 * @author: Voyager
 * @description:
 * @date: 2023/6/26
 **/
public class BannerAdapter extends com.youth.banner.adapter.BannerAdapter<BannerBean.DataBean,BannerAdapter.BannerViewHolder> {

    private List<BannerBean.DataBean> dataBeanList;

    public BannerAdapter(List<BannerBean.DataBean> dataBeanList) {
        super(dataBeanList);

    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        final BannerViewHolder holder=new BannerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner,parent,false));
        holder.itemView.setOnClickListener(v -> {
            Snackbar.make(holder.itemView,"点击",Snackbar.LENGTH_SHORT).show();
        });
        return holder;
    }

    @Override
    public void onBindView(BannerViewHolder holder, BannerBean.DataBean data, int position, int size) {
        holder.textView.setText(data.getTitle());
        Glide.with(holder.imageView)
                .load(data.getImagePath())
                .thumbnail(Glide.with(holder.itemView).load(R.drawable.loading))
                .into(holder.imageView);

    }



    public static class BannerViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.banner_imageView);
            textView=itemView.findViewById(R.id.banner_title);
        }
    }

}
