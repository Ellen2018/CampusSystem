package com.zheng.zhi.campussystem.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.bean.SkinColor;

import java.util.List;

public class DrawerChooseColorAdapter extends RecyclerView.Adapter<DrawerChooseColorAdapter.ColorViewHolder>{

    private Context context;
    private List<SkinColor> skinColors;
    private CallBack callBack;

    public DrawerChooseColorAdapter(Context context, List<SkinColor> skinColors,CallBack callBack){
        this.skinColors = skinColors;
        this.context = context;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_choose_color_drawer,null);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, final int position) {
        SkinColor skinColor = skinColors.get(position);
        holder.top.setBackgroundColor(Color.parseColor(skinColor.getTopColor()));
        holder.left.setBackgroundColor(Color.parseColor(skinColor.getLeftColor()));
        holder.right.setBackgroundColor(Color.parseColor(skinColor.getRightColor()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.chooseSkin(skinColors.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return skinColors.size();
    }

    class ColorViewHolder extends RecyclerView.ViewHolder{

        private View top,left,right;

        public ColorViewHolder(View itemView) {
            super(itemView);
            top = itemView.findViewById(R.id.top_view_color);
            left = itemView.findViewById(R.id.left_view_color);
            right = itemView.findViewById(R.id.right_view_color);
        }
    }

    public interface CallBack{
        void chooseSkin(SkinColor skinColor);
    }

}
