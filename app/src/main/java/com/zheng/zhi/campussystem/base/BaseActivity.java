package com.zheng.zhi.campussystem.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected Context context;
    protected int statusColor = Color.parseColor("#EE6AA7");
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatus();
        setContentView(setLayoutId());
        ButterKnife.bind(this);
        context = this;
        initView();
        initData();
        //横竖屏设置
        if(isSetVerticalScreen() != null){
            if(isSetVerticalScreen()){
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        }
    }

    //设置状态栏
    protected abstract void setStatus();
    //是否横屏竖屏
    protected abstract Boolean isSetVerticalScreen();
    //初始化数据
    protected abstract void initData();
    //初始化视图数据
    protected abstract void initView();
    //设置布局
    protected abstract int setLayoutId();

    public void jumpToActivity(Class clazz){
        Intent intent = new Intent(context,clazz);
        startActivity(intent);
    }

    public void jumpToActivityAndFinish(Class clazz){
        Intent intent = new Intent(context,clazz);
        startActivity(intent);
        finish();
    }

}
