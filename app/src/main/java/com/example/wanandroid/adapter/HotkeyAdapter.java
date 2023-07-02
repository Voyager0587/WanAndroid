package com.example.wanandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.HotkeyBean;

import java.util.Collection;
import java.util.List;
import java.util.zip.Inflater;

/**
 * @className: HotkeyAdapter
 * @author: Voyager
 * @description:
 * @date: 2023/7/2
 **/
public class HotkeyAdapter extends RecyclerView.Adapter<HotkeyAdapter.ViewHolder> {
    private  List<HotkeyBean.DataBean> hotkeyBeanList;

    public HotkeyAdapter(List<HotkeyBean.DataBean> hotkeyBeanList) {
        this.hotkeyBeanList=hotkeyBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotkey,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return hotkeyBeanList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.hotkey);

        }
    }


}
