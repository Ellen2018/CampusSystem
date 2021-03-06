package com.zheng.zhi.campussystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.activity.SerachActivity;
import com.zheng.zhi.campussystem.activity.WebViewActivity;
import com.zheng.zhi.campussystem.dialog.JtlxDialog;
import com.zheng.zhi.campussystem.dialog.LxwmDialog;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeRecyclerViewHolder> {

    private Context context;
    private AppCompatActivity activity;
    private String[] titles = null;
    private boolean isShowWuHanDaXue = true;

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
    public void onBindViewHolder(final HomeRecyclerViewHolder holder, final int position) {
        holder.tv.setText(titles[position]);
        handlerIcon(holder,position);
        holder.rlHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isShowWuHanDaXue){
                    //显示武汉大学的
                    handlerWhdx(holder,position);
                }else {
                    //显示武汉晴川学院
                    handlerWhlj(holder,position);
                }
            }
        });
    }

    private void handlerIcon(HomeRecyclerViewHolder holder, int position) {
        switch (position){
            case 0:
                holder.iv.setImageResource(R.mipmap.xxjj);
                break;
            case 1:
                holder.iv.setImageResource(R.mipmap.yxsz);
                break;
            case 2:
                holder.iv.setImageResource(R.mipmap.xyxw);
                break;
            case 3:
                holder.iv.setImageResource(R.mipmap.tzgg);
                break;
            case 4:
                holder.iv.setImageResource(R.mipmap.jwxx);
                break;
            case 5:
                holder.iv.setImageResource(R.mipmap.jtlx);
                break;
            case 6:
                holder.iv.setImageResource(R.mipmap.jlzx);
                break;
            case 7:
                holder.iv.setImageResource(R.mipmap.rcpy);
                break;
            case 8:
                holder.iv.setImageResource(R.mipmap.xyzb);
                break;
            case 9:
                holder.iv.setImageResource(R.mipmap.lxwm);
                break;
            case 10:
                holder.iv.setImageResource(R.mipmap.zlss);
                break;
        }
    }

    private void handlerWhlj(HomeRecyclerViewHolder holder,int position){
        Intent intent = new Intent(context,WebViewActivity.class);
        intent.putExtra(WebViewActivity.TITLE,titles[position]);
        String url = "";
        switch (titles[position]){
            case "学院简介":
                //url = "http://www.qcuwh.cn/index.php/index-show-tid-2.html";
                //url = "http://www.hbnu.edu.cn/2524/list.htm";
                url = "http://www.xgc.hbnu.edu.cn/2018/1217/c977a78740/page.htm";
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
                Intent intent1 = new Intent(context, SerachActivity.class);
                context.startActivity(intent1);
                break;
        }
        if(url.length() != 0) {
            intent.putExtra(WebViewActivity.URL, url);
            context.startActivity(intent);
        }
    }

    private void handlerWhdx(HomeRecyclerViewHolder holder,int position){
        Intent intent = new Intent(context,WebViewActivity.class);
        //传递标题数据
        intent.putExtra(WebViewActivity.TITLE,titles[position]);
        String url = "";
        switch (titles[position]){
            case "学院简介":
                //url = "http://www.qcuwh.cn/index.php/index-show-tid-2.html";
                url = "http://www.whu.edu.cn/xxgk/xxjj.htm";
                break;
            case "院系设置":
                url = "http://www.whu.edu.cn/jgsz/yxsz.htm";
                break;
            case "校园新闻":
                url = "http://news.whu.edu.cn/";
                break;
            case "通知公告":
                url = "http://www.whu.edu.cn/tzgg.htm";
                break;
            case "教务信息":
                url = "http://www.whu.edu.cn/szdw/lyys.htm";
                break;
            case "交通路线":
                //弹出交通路线的对话框
                JtlxDialog jtlxDialog = new JtlxDialog();
                jtlxDialog.show(activity.getSupportFragmentManager(),"");
                break;
            case "交流中心":
                url = "http://oir.whu.edu.cn/";
                break;
            case "人才培养":
                url = "http://www.whu.edu.cn/rcpy/bksjy.htm";
                break;
            case "校园周边":
                url = "http://www.whu.edu.cn/xxgk/ljyx/lj2018/dec.htm";
                break;
            case "联系我们":
                //url = "http://www.whu.edu.cn/ch_template/img/code-wx.jpg";
                LxwmDialog lxwmDialog = new LxwmDialog();
                lxwmDialog.show(activity.getSupportFragmentManager(),"联系我们");
                break;
            case "站内搜索":
                url = "http://www.whu.edu.cn/vsballsite_search.jsp?wbtreeid=1506";
                break;
        }
        if(url.length() != 0) {
            intent.putExtra(WebViewActivity.URL, url);
            context.startActivity(intent);
        }
    }

    //显示多少个item
    @Override
    public int getItemCount() {
        return titles.length;
    }

    static class HomeRecyclerViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout rlHome;
        //图片
        ImageView iv;
        //文字
        TextView tv;
        public HomeRecyclerViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tv = itemView.findViewById(R.id.tv_title);
            rlHome = itemView.findViewById(R.id.rl_home);
        }
    }
}
