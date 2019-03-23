package com.zheng.zhi.campussystem.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.activity.SerachActivity;
import com.zheng.zhi.campussystem.adapter.SerachHistoryAdapter;
import com.zheng.zhi.campussystem.base.BasePopWindow;

import java.util.List;

import butterknife.BindView;

public class SerachHistoryDialog extends BasePopWindow {

    private List<SerachActivity.Serach> serachList;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private Callback callback;

    public SerachHistoryDialog(Context context, Activity activity, View view, List<SerachActivity.Serach> serachList,Callback callback) {
        super(context, activity, view);
        this.serachList = serachList;
        this.callback = callback;
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_serach_history;
    }

    @Override
    protected int[] setWidthHeight() {
        int[] a = {ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT};
        return a;
    }

    @Override
    protected View setParentView() {
        return null;
    }

    @Override
    protected int showPlace() {
        return Gravity.BOTTOM;
    }

    @Override
    protected Boolean setWinowTransparent() {
        return false;
    }

    @Override
    protected Boolean setOutsideTouchable() {
        return true;
    }

    @Override
    protected Boolean setTouchable() {
        return true;
    }

    @Override
    protected Integer setAnimationStyle() {
        return null;
    }

    @Override
    protected Boolean setShowBackgroundBlack() {
        return false;
    }

    @Override
    protected boolean isFocusable() {
        return false;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new SerachHistoryAdapter(getContext(), serachList, new SerachHistoryAdapter.OnItemClick() {
            @Override
            public void onClick(int position) {
                callback.serach(serachList.get(position).getContent());
            }
        }));
    }

    @Override
    protected Boolean isSetSoftInputMode() {
        return null;
    }

    @Override
    protected void dismissBefore() {

    }

    @Override
    protected Boolean setClippingEnabled() {
        return null;
    }

    public interface Callback{
        void serach(String content);
    }
}
