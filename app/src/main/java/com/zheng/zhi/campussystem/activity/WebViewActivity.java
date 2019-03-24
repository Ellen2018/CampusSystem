package com.zheng.zhi.campussystem.activity;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.base.BaseActivity;
import com.zheng.zhi.campussystem.dialog.WaitDialog;
import com.zheng.zhi.campussystem.utils.ToastUtils;
import com.zheng.zhi.campussystem.utils.WebViewSetttingUtils;
import com.zheng.zhi.campussystem.utils.statusutil.StatusUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private String url = "";

    @OnClick(R.id.back)
    void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    public static String URL = "url";
    public static String TITLE = "title";

    private boolean isOpenWaitDialog = true;
    private List<String> urlList;

    @Override
    protected void setStatus() {
    }

    @Override
    protected Boolean isSetVerticalScreen() {
        return null;
    }

    @Override
    protected void initData() {
        urlList = new ArrayList<>();
        url = getIntent().getStringExtra(URL);
        WebViewSetttingUtils.loadUrl(webView, url, new WebViewSetttingUtils.Callback() {
            @Override
            public void startLoading(String url) {
            }

            @Override
            public void finishLoading(String url) {
                boolean isAdd = false;
                for(String url1:urlList){
                    if(url1.equals(url)){
                        isAdd = true;
                    }
                }
                if(!isAdd){urlList.add(url);}
            }
        });
        tvTitle.setText(getIntent().getStringExtra(TITLE));
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            //处理用户点击的所有url
            handleUrls();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void handleUrls() {
        if(urlList.size() == 1){
            finish();
        }else {
            //首先移除最近的一个url
            urlList.remove(urlList.size() - 1);
            webView.loadUrl(urlList.get(urlList.size() - 1));
        }
    }
}
