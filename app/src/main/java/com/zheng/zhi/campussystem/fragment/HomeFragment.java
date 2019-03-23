package com.zheng.zhi.campussystem.fragment;

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
        recyclerView.setAdapter(new HomeRecyclerViewAdapter(getActivity()));
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_home;
    }
}
