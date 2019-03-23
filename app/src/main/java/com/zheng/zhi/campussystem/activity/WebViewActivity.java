package com.zheng.zhi.campussystem.activity;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.base.BaseActivity;
import com.zheng.zhi.campussystem.utils.WebViewSetttingUtils;

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
        tvTitle.setText(getIntent().getStringExtra(TITLE));
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_webview;
    }
}
