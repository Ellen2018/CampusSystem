package com.zheng.zhi.campussystem.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.activity.SerachActivity;
import com.zheng.zhi.campussystem.activity.WebViewActivity;
import com.zheng.zhi.campussystem.dialog.JtlxDialog;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeRecyclerViewHolder> {

    private Context context;
    private AppCompatActivity activity;
    private String[] titles = null;

    public HomeRecyclerViewAdapter(Context context,AppCompatActivity activity,String[] titles){
        this.context = context;
        this.activity = activity;
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
        switch (titles[position]){
            case "学院简介":
                url = "http://www.qcuwh.cn/index.php/index-show-tid-2.html";
                break;
            case "院系设置":
                url = "http://www.qcuwh.cn/index.php/index-show-tid-6.html";
                break;
            case "校园新闻":
                url = "http://www.qcuwh.cn/index.php/index-show-tid-39.html";
                break;
            case "通知公告":
                url = "http://www.qcuwh.cn/index.php/index-view-aid-11622.html";
                break;
            case "教务信息":
                url = "http://xxgk.qcuwh.cn/";
                break;
            case "交通路线":
                //弹出交通路线的对话框
                JtlxDialog jtlxDialog = new JtlxDialog();
                jtlxDialog.show(activity.getSupportFragmentManager(),"");
                break;
            case "交流中心":
                url = "http://news.qcuwh.cn/";
                break;
            case "人才培养":
                url = "http://www.qcuwh.cn/index.php/index-show-tid-19.html";
                break;
            case "校园周边":
                url = "http://www.qcuwh.cn/index.php/index-show-tid-31.html";
                break;
            case "联系我们":
                url = "http://www.qcuwh.cn/index.php/index-show-tid-1073.html";
                break;
            case "站内搜索":
                //url = "http://www.qcuwh.cn/index.php/commonform-search.html?key=计算机&button=+";
                Intent intent1 = new Intent(context, SerachActivity.class);
                context.startActivity(intent1);
                break;
        }
        if(url.length() != 0) {
            intent.putExtra(WebViewActivity.URL, url);
            context.startActivity(intent);
        }
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
