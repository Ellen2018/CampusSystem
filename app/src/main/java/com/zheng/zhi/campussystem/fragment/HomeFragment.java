package com.zheng.zhi.campussystem.fragment;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.youth.banner.Banner;
import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.adapter.HomeRecyclerViewAdapter;
import com.zheng.zhi.campussystem.base.BaseFragment;
import com.zheng.zhi.campussystem.utils.GlideImageLoader;

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

    /**
     * 武汉大学珞珈学院新校园位于湖北省武汉市东湖高新技术开发区中华科技产业园玉屏大道9号，驾车可导航武汉晴川学院为终点站即可到达。
     *
     * 在珞瑜路光谷广场乘坐922、909公交可直达 。
     *
     *
     *
     *
     * 乘车路线：
     *
     * 1、在武昌火车站或武汉火车站乘地铁4号线到中南路站或洪山广场站换乘2号线至光谷广场站，在珞瑜路光谷广场站转乘922路公交或在珞瑜路鲁巷站转乘909路公交至兴龙街龙泉站（武汉晴川学院）；
     *
     * 2、在汉口火车站乘地铁2号线至光谷广场站，在珞瑜路光谷广场站转乘922路公交或在珞瑜路鲁巷站转乘909路公交至兴龙街龙泉站（武汉晴川学院）。
     *
     * 3、自驾车导航武汉晴川学院即可到达。
     */

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
