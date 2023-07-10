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
 * @description: 热词的适配器
 * @date: 2023/7/2
 **/
public class HotkeyAdapter extends RecyclerView.Adapter<HotkeyAdapter.ViewHolder> {
    private  List<HotkeyBean.DataBean> hotkeyBeanList;
    //监听器，便于在Fragment中操作item的监听事件
    private OnListener mListener;


    public void setmListener(OnListener mListener) {
        this.mListener = mListener;
    }

    public HotkeyAdapter(List<HotkeyBean.DataBean> hotkeyBeanList) {
        this.hotkeyBeanList=hotkeyBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotkey,parent,false);
        ViewHolder holder=new ViewHolder(view);
        holder.itemView.setOnClickListener(v -> {
            if(mListener!=null){
                mListener.onItemClick(v,holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(hotkeyBeanList.get(position).getName());

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

    public interface OnListener {
        void onItemClick(View view, int position);

    }


}
