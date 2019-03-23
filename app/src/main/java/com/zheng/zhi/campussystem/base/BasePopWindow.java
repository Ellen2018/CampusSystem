package com.zheng.zhi.campussystem.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
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
    private Integer width;
    private Integer hight;
    private OnDismissListener onDismissListener;

    public OnDismissListener getOnDismissListener() {
        return onDismissListener;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHight() {
        return hight;
    }

    public void setHight(Integer hight) {
        this.hight = hight;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public View getParentView() {
        return parentView;
    }

    public void setParentView(View parentView) {
        this.parentView = parentView;
    }

    public BasePopWindow(Context context, Activity activity, View view) {
        this.context = context;
        this.activity = activity;
        this.parentView = view;
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

    //设置宽高，只会按照数组的前两个进行设置，[0]->宽，[test1]->高
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

    protected abstract boolean isFocusable();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract Boolean isSetSoftInputMode();

    protected abstract void dismissBefore();

    //设置全屏显示
    protected abstract Boolean setClippingEnabled();

    //显示
    @SuppressLint("WrongConstant")
    private void init() {
        if(width != null && hight != null){
            popupWindow = new PopupWindow(setView(), width, hight, isFocusable());
        }else {
            popupWindow = new PopupWindow(setView(), setWidthHeight()[0], setWidthHeight()[1], isFocusable());
        }
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
        //适配虚拟按键
        if(isSetSoftInputMode()!= null && isSetSoftInputMode()) {
            popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            //设置弹出窗体需要软键盘
            popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        }
        if(setClippingEnabled() != null){
            popupWindow.setClippingEnabled(setClippingEnabled());
        }
    }

    public void show() {
        initView();
        initData();
        if (setShowBackgroundBlack() != null && setShowBackgroundBlack()) {
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    //去掉暗色背景
                    WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                    lp.alpha = 1.0f;
                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    activity.getWindow().setAttributes(lp);

                    if(onDismissListener != null){
                        onDismissListener.dissmiss();
                    }
                }
            });
        }else {
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if(onDismissListener != null){
                        onDismissListener.dissmiss();
                    }
                }
            });
        }
        //这个地方显示的位置还需要进一步封装
        if(setParentView() == null){
            popupWindow.showAtLocation(parentView, showPlace(), 0, 0);
        }else {
            popupWindow.showAtLocation(setParentView(), showPlace(), 0, 0);
        }
        //是否背景变暗
        if (setShowBackgroundBlack() != null &&setShowBackgroundBlack()) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = 0.3f;
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            activity.getWindow().setAttributes(lp);
        }
    }

    public void showBottom() {
        initView();
        initData();
        if (setShowBackgroundBlack() != null && setShowBackgroundBlack()) {
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    //去掉暗色背景
                    WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                    lp.alpha = 1.0f;
                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    activity.getWindow().setAttributes(lp);

                    if(onDismissListener != null){
                        onDismissListener.dissmiss();
                    }
                }
            });
        }else {
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if(onDismissListener != null){
                        onDismissListener.dissmiss();
                    }
                }
            });
        }
        if(setParentView() != null){
            popupWindow.showAsDropDown(setParentView());
        }else {
            popupWindow.showAsDropDown(parentView);
        }
        //是否背景变暗
        if (setShowBackgroundBlack() != null &&setShowBackgroundBlack()) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = 0.3f;
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            activity.getWindow().setAttributes(lp);
        }
    }

    //适配虚拟键盘,偏差值为dpValue
    public void showBottom(int dpValue) {
        initView();
        initData();
        if (setShowBackgroundBlack() != null && setShowBackgroundBlack()) {
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    //去掉暗色背景
                    WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                    lp.alpha = 1.0f;
                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    activity.getWindow().setAttributes(lp);

                    if(onDismissListener != null){
                        onDismissListener.dissmiss();
                    }
                }
            });
        }else {
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if(onDismissListener != null){
                        onDismissListener.dissmiss();
                    }
                }
            });
        }
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        // 屏幕高度（像素）
        int height = dm.heightPixels;
        //获取虚拟键盘高度
        float density = dm.density;// 屏幕密度（0.75 / 1.0 / 1.5）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenHeight = (int) (height / density);// 屏幕高度(dp)
        popupWindow.showAtLocation(parentView, Gravity.NO_GRAVITY, 0, (int) ((screenHeight - dpValue)*density));
        //是否背景变暗
        if (setShowBackgroundBlack() != null &&setShowBackgroundBlack()) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = 0.3f;
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            activity.getWindow().setAttributes(lp);
        }
    }

    public void dismiss(){
        //取消ButterKnife的绑定，释放资源
        dismissBefore();
        unbinder.unbind();
        popupWindow.dismiss();
        popupWindow = null;
    }

    public interface OnDismissListener{
        void dissmiss();
    }
}
