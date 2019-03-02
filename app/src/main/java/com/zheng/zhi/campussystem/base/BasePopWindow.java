package com.zheng.zhi.campussystem.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BasePopWindow {

    private PopupWindow popupWindow;
    private Context context;
    private Activity activity;
    private Unbinder unbinder;
    private View parentView;

    public View getParentView() {
        return parentView;
    }

    public void setParentView(View parentView) {
        this.parentView = parentView;
    }

    public BasePopWindow(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        init();
    }

    private View setView() {
        View contentView = LayoutInflater.from(context).inflate(setLayout(), null, false);
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        unbinder = ButterKnife.bind(this, contentView);
        return contentView;
    }

    //绑定布局，返回view
    protected abstract int setLayout();

    //设置宽高，只会按照数组的前两个进行设置，[0]->宽，[1]->高
    protected abstract int[] setWidthHeight();

    //设置PopWindow的目标View
    protected abstract View setParentView();

    //设置位置
    protected abstract int showPlace();

    //设置Popwindow的背景是否透明
    protected abstract Boolean setWinowTransparent();

    //设置PopupWindow是否能响应外部点击事件
    protected abstract Boolean setOutsideTouchable();

    //设置PopupWindow是否能响应点击事件
    protected abstract Boolean setTouchable();

    //添加动画效果
    protected abstract Integer setAnimationStyle();

    //显示的时候背景是否变暗
    protected abstract Boolean setShowBackgroundBlack();

    protected abstract void initView();

    protected abstract void initData();

    //显示
    private void init() {
        popupWindow = new PopupWindow(setView(), setWidthHeight()[0], setWidthHeight()[1], true);
        //设置背景是否透明
        if (setWinowTransparent() != null) {
            if (setWinowTransparent()) {
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
        //设置是否相应外部点击事件
        if (setOutsideTouchable() != null) {
            popupWindow.setOutsideTouchable(setOutsideTouchable());
        }
        //设置是否相应点击事件
        if (setTouchable() != null) {
            popupWindow.setTouchable(setTouchable());
        }
        //设置动画效果
        if (setAnimationStyle() != null) {
            popupWindow.setAnimationStyle(setAnimationStyle());
        }
    }

    public void show() {
        initView();
        initData();
        if (setShowBackgroundBlack() != null && setShowBackgroundBlack()) {
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                    lp.alpha = 1.0f;
                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    activity.getWindow().setAttributes(lp);
                }
            });
        }
        popupWindow.showAtLocation(setParentView(), showPlace(), 0, 0);
        //是否背景变暗
        if (setShowBackgroundBlack() != null &&setShowBackgroundBlack()) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = 0.3f;
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            activity.getWindow().setAttributes(lp);
        }
    }

    public void dissmiss(){
        popupWindow.dismiss();
        popupWindow = null;
    }


}
