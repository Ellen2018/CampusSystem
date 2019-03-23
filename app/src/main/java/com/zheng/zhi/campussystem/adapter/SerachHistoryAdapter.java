package com.zheng.zhi.campussystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.activity.SerachActivity;

import java.util.List;

public class SerachHistoryAdapter extends RecyclerView.Adapter<SerachHistoryAdapter.SerachHistoryViewHolder> {

    private Context context;
    private List<SerachActivity.Serach> serachList;
    private OnItemClick onItemClick;

    public SerachHistoryAdapter(Context context, List<SerachActivity.Serach> serachList,OnItemClick onItemClick) {
        this.context = context;
        this.serachList = serachList;
        this.onItemClick = onItemClick;
    }

    @Override
    public SerachHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_serach_history, parent, false);
        return new SerachHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SerachHistoryViewHolder holder, final int position) {
        holder.tvContent.setText(serachList.get(position).getContent()+"("+serachList.get(position).getCount()+")");
        holder.tvCount.setText(serachList.get(position).getSerachDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serachList == null ? 0 : serachList.size();
    }


    static class SerachHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView tvContent,tvCount;

        public SerachHistoryViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_serach_content);
            tvCount = itemView.findViewById(R.id.tv_serach_count);
        }
    }

    public interface OnItemClick{
        void onClick(int position);
    }

}
