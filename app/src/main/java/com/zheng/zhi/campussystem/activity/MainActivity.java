package com.zheng.zhi.campussystem.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.base.BaseActivity;
import com.zheng.zhi.campussystem.base.BaseFragment;
import com.zheng.zhi.campussystem.fragment.GradeFragment;
import com.zheng.zhi.campussystem.fragment.HomeFragment;
import com.zheng.zhi.campussystem.fragment.MoreFragment;
import com.zheng.zhi.campussystem.utils.statusutil.StatusUtils;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private static String STRING_HOME = "首页";
    private static String STRING_GRADE = "新闻";
    private static String STRING_MORE = "更多";

    private BaseFragment currentFragment;

    private String[] titles = {STRING_HOME,STRING_GRADE,STRING_MORE};

    @Override
    protected void setStatus() {
    }

    @Override
    protected Boolean isSetVerticalScreen() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        BottomNavigationItem bottomNavigationItem1 = new BottomNavigationItem(R.mipmap.sy,titles[0]);
        BottomNavigationItem bottomNavigationItem2 = new BottomNavigationItem(R.mipmap.xw,titles[1]);
        BottomNavigationItem bottomNavigationItem3 = new BottomNavigationItem(R.mipmap.gd,titles[2]);
        bottomNavigationBar.addItem(bottomNavigationItem1);
        bottomNavigationBar.addItem(bottomNavigationItem2);
        bottomNavigationBar.addItem(bottomNavigationItem3);
        bottomNavigationBar.setBarBackgroundColor("#ffffff") // 背景颜色
                .setInActiveColor("#bfbfbf") // 未选中状态颜色
                .setActiveColor("#1296db"); // 选中状态颜色
        bottomNavigationBar.initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
           @Override
           public void onTabSelected(int position) {
               replaceFragment(titles[position]);
           }

           @Override
           public void onTabUnselected(int position) {

           }

           @Override
           public void onTabReselected(int position) {

           }
       });
        replaceFragment(titles[0]);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    private void replaceFragment(String tag) {
        tvTitle.setText(tag);
        if (currentFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();
        }
        currentFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(tag);
        if (currentFragment == null) {
            switch (tag) {
                case "首页":
                    currentFragment = new HomeFragment();
                    break;
                case "新闻":
                    currentFragment = new GradeFragment();
                    break;
                case "更多":
                    currentFragment = new MoreFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.fl_main, currentFragment, tag).commit();
        }else {
            getSupportFragmentManager().beginTransaction().show(currentFragment).commit();
        }
    }
}
