package com.zheng.zhi.campussystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.activity.WebViewActivity;
import com.zheng.zhi.campussystem.bean.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {


    private Context context;
    private News news;

    public NewsAdapter(Context context,News news) {
        this.context = context;
        this.news = news;
    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, final int position) {
        News.DataBean.TechBean techBean = news.getData().getTech().get(position);
        if(techBean != null) {
            if(techBean.getPicInfo() != null && techBean.getPicInfo().size() > 0)
            Glide.with(context).load(techBean.getPicInfo().get(0).getUrl()).into(holder.imageView);
        }
        holder.textView.setText(news.getData().getDy().get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(WebViewActivity.URL,news.getData().getDy().get(position).getLink());
                intent.putExtra(WebViewActivity.TITLE,"新闻");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return news == null ? 0 : news.getData().getDy().size();
    }

    static class NewsAdapterViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public NewsAdapterViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_news);
            textView = itemView.findViewById(R.id.tv_news_title);
        }
    }

}
