package com.zheng.zhi.campussystem.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.activity.SerachActivity;
import com.zheng.zhi.campussystem.adapter.SerachHistoryAdapter;
import com.zheng.zhi.campussystem.base.BasePopwindow;

import java.util.List;

import butterknife.BindView;

public class SerachHistoryDialog extends BasePopwindow implements BasePopwindow.ButterKnifeInterface {


    public SerachHistoryDialog(Activity activity, Context context) {
        super(activity, context);
    }

    @Override
    protected View onCreateView() {
        return null;
    }

    @Override
    protected int setWidth() {
        return 0;
    }

    @Override
    protected int setHeight() {
        return 0;
    }

    @Override
    protected boolean isGetFocus() {
        return false;
    }

    @Override
    protected Boolean isSetBackgroundDrawable() {
        return null;
    }

    @Override
    protected Drawable setBackgroundDrawable() {
        return null;
    }

    @Override
    protected boolean isResponseOutsideTouchable() {
        return false;
    }

    @Override
    protected boolean isResponseTouchable() {
        return false;
    }

    @Override
    protected void setOtherSetting(PopupWindow popupWindow) {

    }

    @Override
    protected Boolean isSetShowBackgroundBlack() {
        return null;
    }

    @Override
    protected void dismissBefore() {

    }

    @Override
    public void initButterKnife(View view) {

    }

    @Override
    public void unBindButterKnife() {

    }
}
