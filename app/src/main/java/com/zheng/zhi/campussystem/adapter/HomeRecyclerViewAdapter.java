package com.zheng.zhi.campussystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.activity.WebViewActivity;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeRecyclerViewHolder> {

    public Context context;

    public HomeRecyclerViewAdapter(Context context){
        this.context = context;
    }

    @Override
    public HomeRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new HomeRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeRecyclerViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(WebViewActivity.URL,"http://www.qcuwh.cn/");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 11;
    }

    static class HomeRecyclerViewHolder extends RecyclerView.ViewHolder{
        public HomeRecyclerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
