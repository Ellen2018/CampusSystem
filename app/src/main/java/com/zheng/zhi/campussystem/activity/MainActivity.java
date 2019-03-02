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

    private BaseFragment currentFragment;

    private String[] titles = {"首页","等级","更多"};

    @Override
    protected void setStatus() {
        StatusUtils.setTranslucentStatus(this);
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
        BottomNavigationItem bottomNavigationItem1 = new BottomNavigationItem(R.mipmap.ic_launcher,titles[0]);
        BottomNavigationItem bottomNavigationItem2 = new BottomNavigationItem(R.mipmap.ic_launcher,titles[1]);
        BottomNavigationItem bottomNavigationItem3 = new BottomNavigationItem(R.mipmap.ic_launcher,titles[2]);
        bottomNavigationBar.addItem(bottomNavigationItem1);
        bottomNavigationBar.addItem(bottomNavigationItem2);
        bottomNavigationBar.addItem(bottomNavigationItem3);
        bottomNavigationBar.initialise();
        bottomNavigationBar.setActiveColor(R.color.colorAccent);
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
                case "等级":
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
