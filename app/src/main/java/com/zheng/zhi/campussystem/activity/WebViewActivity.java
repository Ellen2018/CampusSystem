package com.zheng.zhi.campussystem.activity;

import android.webkit.WebView;

import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.base.BaseActivity;
import com.zheng.zhi.campussystem.utils.WebViewSetttingUtils;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.web_view)
    WebView webView;

    private String url = "";

    public static String URL = "url";

    @Override
    protected void setStatus() {

    }

    @Override
    protected Boolean isSetVerticalScreen() {
        return null;
    }

    @Override
    protected void initData() {
        url = getIntent().getStringExtra(URL);
        WebViewSetttingUtils.loadUrl(webView,url);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_webview;
    }
}
