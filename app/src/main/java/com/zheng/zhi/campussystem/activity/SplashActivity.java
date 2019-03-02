package com.zheng.zhi.campussystem.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.base.BaseActivity;
import com.zheng.zhi.campussystem.utils.statusutil.StatusUtils;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity {

    private static final int TIME = 5;
    private SplashHandler splashHandler;

    private boolean isClickTv = false;

    @BindView(R.id.tv_jump)
    TextView tvJump;

    @OnClick(R.id.tv_jump)
    void onClick(View view){
        switch (view.getId()){
            case R.id.tv_jump:
                isClickTv = true;
                jumpToActivityAndFinish(MainActivity.class);
                break;
        }
    }

    @Override
    protected void setStatus() {
        //设置全屏
       StatusUtils.setFullScreen(this);
    }

    @Override
    protected Boolean isSetVerticalScreen() {
        return true;
    }

    @Override
    protected void initData() {
        splashHandler = new SplashHandler(this);
    }

    @Override
    protected void initView() {
       tvJump.setText("跳过:"+TIME);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //开启线程进行倒计时任务
        new SplashThread(this,TIME).start();
    }


    static class SplashThread extends Thread{
        private int time;
        private WeakReference<SplashActivity> weakReference;

        public SplashThread(SplashActivity splashActivity,int time){
            this.time = time;
            weakReference = new WeakReference<>(splashActivity);
        }

        @Override
        public void run() {
            super.run();
            SplashActivity splashActivity = weakReference.get();
            for(int i = 1;i<=time && !splashActivity.isClickTv ;i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = i;
                splashActivity.splashHandler.sendMessage(message);
            }
        }
    }

    static class SplashHandler extends Handler {

        private WeakReference<SplashActivity> weakReference;
        public SplashHandler(SplashActivity splashActivity){
            weakReference = new WeakReference<>(splashActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SplashActivity splashActivity = weakReference.get();
            splashActivity.tvJump.setText("跳过:"+(TIME - msg.what));
            if(msg.what == TIME && !weakReference.get().isClickTv){
                weakReference.get().jumpToActivityAndFinish(MainActivity.class);
            }
        }
    }
}
