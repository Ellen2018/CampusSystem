package com.zheng.zhi.campussystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.activity.WebViewActivity;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeRecyclerViewHolder> {

    public Context context;
    private String[] titles = null;

    public HomeRecyclerViewAdapter(Context context,String[] titles){
        this.context = context;
        this.titles = titles;
    }

    @Override
    public HomeRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new HomeRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeRecyclerViewHolder holder, final int position) {
        holder.tv.setText(titles[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler(position);
            }
        });
    }

    private void handler(int position){
        Intent intent = new Intent(context,WebViewActivity.class);
        intent.putExtra(WebViewActivity.TITLE,titles[position]);
        String url = "";
        switch (position){
            case 0:
                //学院简介
                url = "http://www.qcuwh.cn/index.php/index-show-tid-2.html";
                break;
            case 1:
                //院系设置
                url = "http://www.qcuwh.cn/index.php/index-show-tid-6.html";
                break;
            case 2:
                //校园新闻
                url = "http://www.qcuwh.cn/index.php/index-show-tid-39.html";
                break;
            case 3:
                //通知公告
                break;
            case 4:
                //教务信息
                break;
            case 5:
                //校车查询
                break;
            case 6:
                //交流中心
                break;
            case 7:
                //图书馆
                break;
            case 8:
                //校园周边
                break;
            case 9:
                //联系我们
                break;
            case 10:
                //站内搜索
                break;
        }
        intent.putExtra(WebViewActivity.URL,url);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    static class HomeRecyclerViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv;
        public HomeRecyclerViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tv = itemView.findViewById(R.id.tv_title);
        }
    }
}
