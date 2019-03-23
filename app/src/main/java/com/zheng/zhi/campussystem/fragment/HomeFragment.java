package com.zheng.zhi.campussystem.fragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.activity.WebViewActivity;
import com.zheng.zhi.campussystem.adapter.HomeRecyclerViewAdapter;
import com.zheng.zhi.campussystem.base.BaseFragment;
import com.zheng.zhi.campussystem.utils.GlideImageLoader;
import com.zheng.zhi.campussystem.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private int[] iamgeIdArray = {R.mipmap.i1,R.mipmap.i2,R.mipmap.i3,R.mipmap.i4,R.mipmap.i5};
    private String[] titleString = {"学院简介","院系设置","校园新闻","通知公告","教务信息",
            "交通路线","交流中心","人才培养","校园周边","联系我们","站内搜索"};

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        banner.setImageLoader(new GlideImageLoader());
        List<Integer> imageList = new ArrayList<>();
       for(int imageId:iamgeIdArray){
           imageList.add(imageId);
       }
        banner.isAutoPlay(true);
        banner.setDelayTime(2000);
        banner.setImages(imageList);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(WebViewActivity.TITLE,"校园头条");
                String url = "";
                if(position == 0){
                    url = "http://www.qcuwh.cn/index.php/index-view-aid-6411.html";
                }else if(position == 1){
                    url = "http://www.qcuwh.cn/index.php/index-view-aid-6222.html";
                }else if(position == 2){
                    url = "http://www.qcuwh.cn/index.php/index-view-aid-10108.html";
                }else if(position == 3){
                    url = "http://xyzx.qcuwh.cn/index.php/index-show-tid-1270.html";
                }else {
                    url = "http://www.qcuwh.cn/index.php/index-view-aid-6230.html";
                }
                if(url.length() >0) {
                    intent.putExtra(WebViewActivity.URL, url);
                    startActivity(intent);
                }
            }
        });
        banner.start();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),4);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new HomeRecyclerViewAdapter(getActivity(), (AppCompatActivity) getActivity(),titleString));
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_home;
    }
}
