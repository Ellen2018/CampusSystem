package com.zheng.zhi.campussystem.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.adapter.NewsAdapter;
import com.zheng.zhi.campussystem.base.BaseFragment;
import com.zheng.zhi.campussystem.bean.NewsBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsFragment extends BaseFragment implements BaseFragment.ButterKnifeInterface {

    private Unbinder unbinder;

    //https://blog.csdn.net/c__chao/article/details/78573737
    private String suiJIurl = "https://www.apiopen.top/satinApi?type=1&page=0";
    private String url = "https://www.apiopen.top/satinApi?";

    private static final int GET_DATA_FAILURE = 0;
    private static final int GET_DATA_SUCCESS_UP = 1;
    private static final int GET_DATA_SUCCESS_DOWN = 2;

    @BindView(R.id.spring_view)
    SpringView springView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private Pager pager;

    private List<NewsBean.DataBean> dataBeanList;
    private NewsAdapter newsAdapter;
    private RefreshDataHandler refreshDataHandler;

    @Override
    protected void initData() {
        pager = new Pager();
        pager.setType(4);
        refreshDataHandler = new RefreshDataHandler();
        onLoadMoreData(true,pager.getUrl());
    }

    private void onLoadMoreData(final boolean isLoadMore, String url){
        OkHttpClient client=new OkHttpClient();
        final Request request=new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                refreshDataHandler.sendEmptyMessage(GET_DATA_FAILURE);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(response.isSuccessful()) {
                    final String json = response.body().string();
                    NewsBean newsBean = new Gson().fromJson(json, NewsBean.class);
                    if (dataBeanList == null) {
                        dataBeanList = new ArrayList<>();
                    }
                    if (isLoadMore) {
                        //上拉加载更多
                        dataBeanList.addAll(newsBean.getData());
                        refreshDataHandler.sendEmptyMessage(GET_DATA_SUCCESS_UP);
                    } else {
                        //下拉刷新
                        dataBeanList.addAll(0, newsBean.getData());
                        refreshDataHandler.sendEmptyMessage(GET_DATA_SUCCESS_DOWN);
                    }
                }else {
                    refreshDataHandler.sendEmptyMessage(GET_DATA_FAILURE);
                }
            }

        });
    }


    @Override
    protected void initView() {
        springView.setHeader(new DefaultHeader(getActivity()));
        springView.setFooter(new DefaultFooter(getActivity()));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                onLoadMoreData(false,suiJIurl);
            }

            @Override
            public void onLoadmore() {
                pager.setPager(pager.getPager()+1);
                onLoadMoreData(true,pager.getUrl());
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_news;
    }

    @Override
    public void initButterKnife(View view) {
        unbinder = ButterKnife.bind(this,view);
    }

    @Override
    public void unBindButterKnife() {
        unbinder.unbind();
    }

    private static class Pager{
        int type = 0;
        int pager = 0;

        public String getUrl(){
            return String.format("https://www.apiopen.top/satinApi?type=%d&page=%d",type,pager);
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPager() {
            return pager;
        }

        public void setPager(int pager) {
            this.pager = pager;
        }
    }

    class RefreshDataHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GET_DATA_FAILURE:
                    Toast.makeText(getActivity(),"对不起，刷新数据失败",Toast.LENGTH_SHORT).show();
                    break;
                case GET_DATA_SUCCESS_DOWN:
                    if(newsAdapter == null){
                        newsAdapter = new NewsAdapter(getActivity(),dataBeanList);
                        recyclerView.setAdapter(newsAdapter);
                    }else {
                        newsAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "下拉刷新数据成功！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case GET_DATA_SUCCESS_UP:
                    if(newsAdapter == null){
                        newsAdapter = new NewsAdapter(getActivity(),dataBeanList);
                        recyclerView.setAdapter(newsAdapter);
                    }else {
                        newsAdapter.notifyDataSetChanged();
                    }
                    Toast.makeText(getActivity(),"上拉加载更多数据成功!",Toast.LENGTH_SHORT).show();
                    break;
            }
            springView.onFinishFreshAndLoad();
        }
    }

}
