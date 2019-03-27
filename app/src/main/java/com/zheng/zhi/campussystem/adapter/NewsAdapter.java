package com.zheng.zhi.campussystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.bean.NewsBean;

import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {


    private Context context;
    private List<NewsBean.DataBean> dataBeanList;

    public NewsAdapter(Context context, List<NewsBean.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList = dataBeanList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, null);
        return new NewsViewHolder(view);
    }

    /**
     * type=41 : 视频
     * type=10 : 图片
     * type=29 : 段子
     * type=31 : 声音
     * @param holder
     * @param position
     */

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.iv.setImageBitmap(null);
        final NewsBean.DataBean dataBean = dataBeanList.get(position);
        if(dataBean.getType().equals("41")){
            //视频
            Glide.with(context).load(dataBean.getBimageuri()).into(holder.iv);
            holder.tvContent.setText(dataBean.getText()+"[视频]");
            holder.jzvdStd.setUp(dataBean.getVideouri(), dataBean.getText() , Jzvd.SCREEN_NORMAL);
            Glide.with(context).load(dataBean.getBimageuri()).into(holder.jzvdStd.thumbImageView);
        }else if(dataBean.getType().equals("10")){
            //图片
            Glide.with(context).load(dataBean.getImage0()).into(holder.iv);
            holder.tvContent.setText(dataBean.getText()+"[图片]");
        }else if(dataBean.getType().equals("29")){
            //段子
            Glide.with(context).load(dataBean.getBimageuri()).into(holder.iv);
            holder.tvContent.setText(dataBean.getText()+"[段子]");
        }
    }

    @Override
    public int getItemCount() {
        return dataBeanList == null ? 0 : dataBeanList.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView tvContent;
        ImageView iv;
        JzvdStd jzvdStd;

        public NewsViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
            iv = itemView.findViewById(R.id.iv);
            jzvdStd = itemView.findViewById(R.id.videoplayer);
        }

    }

}
