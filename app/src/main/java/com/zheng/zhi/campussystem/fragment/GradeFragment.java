package com.zheng.zhi.campussystem.fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.adapter.NewsAdapter;
import com.zheng.zhi.campussystem.base.BaseFragment;
import com.zheng.zhi.campussystem.bean.News;
import com.zheng.zhi.campussystem.utils.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GradeFragment extends BaseFragment {

    //这页显示新闻吧
    //请求地址
    String url = "https://www.apiopen.top/journalismApi";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private Handler handler = new Handler();

    @Override
    protected void initData() {
        //1,创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
//2,创建一个Request
        final Request request = new Request.Builder()
                .url(url)
                .build();
//3,新建一个call对象
        Call call = mOkHttpClient.newCall(request);
//4，请求加入调度，这里是异步Get请求回调
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.e("json数据", json);
                Gson gson = new Gson();
                final News news = gson.fromJson(json,News.class);
                //标题
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(new NewsAdapter(getActivity(),news));
                    }
                });

            }
        });


    }

    @Override
    protected void initView() {
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_grade;
    }
}
