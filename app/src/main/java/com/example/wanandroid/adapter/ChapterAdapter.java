package com.example.wanandroid.adapter;

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
 * @description: 二级分类标题chapterName的适配器
 * @date: 2023/7/5
 **/
public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {

    public static int superPosition = 0;
    public static int secondPosition = 0;

    /**
     * 只会在点击二级分类后，通过外部进行改变
     * 持久化缓存点击二级分类后的一级和二级分类的位置，以达到点击二级分类的item后，再点击其他一级分类的item，再点击回之前的一级·分类的item，被选中状态仍然存在
     */
    private static String superJudge = "0";
    private static String secondJudge = "0";

    List<ChapterBean.DataBean.ChildrenBean> childrenBeanList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setSecondPosition(int secondPosition) {
        ChapterAdapter.secondPosition = secondPosition;
    }

    public static String getSuperJudge() {
        return superJudge;
    }

    public static String getSecondJudge() {
        return secondJudge;
    }

    public void setSuperJudge(String superJudge) {
        ChapterAdapter.superJudge = superJudge;
    }


    public void setSecondJudge(String secondJudge) {
        ChapterAdapter.secondJudge = secondJudge;
    }


    public void setSuperPosition(int superPosition) {
        ChapterAdapter.superPosition = superPosition;
    }

    public ChapterAdapter(List<ChapterBean.DataBean.ChildrenBean> childrenBeanList) {
        this.childrenBeanList = childrenBeanList;
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter_name, parent, false);
        ChapterViewHolder holder = new ChapterViewHolder(view);
        holder.itemView.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {

        holder.itemView.setOnClickListener(v -> {
        });

        holder.textView.setText(childrenBeanList.get(position).getName());
        /**
         * 本逻辑在点击一级标题或二级标题后会执行
         * 判断是否为点击二级分类后触发的刷新：notifyItemChanged,如果现在的item的一级和二级跟点击二级item后记录的一级和二级位置相同，就设置为选中状态
         */
        if (superPosition == Integer.parseInt(superJudge) && position == Integer.parseInt(secondJudge)) {
            holder.itemView.setBackgroundResource(holder.bgBlue);
        } else {
            holder.itemView.setBackgroundResource(holder.bgWhite);
        }
        holder.textView.setOnClickListener(v -> {


            //我觉得点击二级分类后，再点击其他一级分类，之后再点击之前的那个一级分类，二级分类显示未被选中，原因是点击其他一级分类会刷新二级分类的adapter中的数据，因此应该记录值，Fragment中应该记录一级标题和二级标题的数据，
            //检测到未真正改变时，就传值给secondAdapter，只需要传入二级的位置，检测逻辑全都在Fragment中，adapter只需要有一个方法来让selectedPosition=holder.getAdapterPosition就好，然后notify

            onItemClickListener.onItemClick(v, holder.getAdapterPosition());
        });
    }


    @Override
    public int getItemCount() {
        return childrenBeanList.size();
    }

    public static class ChapterViewHolder extends RecyclerView.ViewHolder {
        int bgBlue, bgWhite;
        TextView textView;
        RelativeLayout chapter_layout;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.chapterName);
            chapter_layout = itemView.findViewById(R.id.recyclerView_search);
            bgWhite = R.drawable.item_chapter;
            bgBlue = R.drawable.home_round_100;
        }
    }

}
