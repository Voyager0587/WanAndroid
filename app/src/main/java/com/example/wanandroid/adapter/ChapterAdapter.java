package com.example.wanandroid.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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

    private int selectedPosition = -1;
    List<ChapterBean.DataBean.ChildrenBean> childrenBeanList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ChapterAdapter(List<ChapterBean.DataBean.ChildrenBean> childrenBeanList) {
        this.childrenBeanList = childrenBeanList;
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter_name,parent,false);
        ChapterViewHolder holder=new ChapterViewHolder(view);
        holder.itemView.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        holder.itemView.setBackgroundColor(selectedPosition == position ? Color.BLUE : Color.WHITE);
        holder.itemView.setOnClickListener(v -> {
        });

        holder.textView.setText(childrenBeanList.get(position).getName());


        if (position == selectedPosition) {
            holder.itemView.setBackgroundResource(holder.bgBlue);
        } else {
            holder.itemView.setBackgroundResource(holder.bgWhite);
        }
        holder.textView.setOnClickListener(v -> {

            if (selectedPosition != -1) {
                notifyItemChanged(selectedPosition);
            }
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(selectedPosition);
            //我觉得点击二级分类后，再点击其他一级分类，之后再点击之前的·那个一级分类，二级分类显示未被选中，原因是点击其他一级分类会刷新二级分类的adapter中的数据，因此应该记录值，Fragment中应该记录一级标题和二级标题的数据，
            //检测到未真正改变时，就传值给secondAdapter，只需要传入二级的位置，检测逻辑全都在Fragment中，adapter只需要有一个方法来让selectedPosition=holder.getAdapterPosition就好，然后notify


            onItemClickListener.onItemClick(v, holder.getAdapterPosition());

        });
    }


    @Override
    public int getItemCount() {
        return childrenBeanList.size();
    }

    public static class ChapterViewHolder extends RecyclerView.ViewHolder{
        int bgBlue, bgWhite;
        TextView textView;
        RelativeLayout chapter_layout;
        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.chapterName);
            chapter_layout=itemView.findViewById(R.id.recyclerView_search);
            bgWhite=R.drawable.round_20;
            bgBlue=R.drawable.person_round_100;
        }
    }

}
