package com.zheng.zhi.campussystem.utils;

import android.graphics.Bitmap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewSetttingUtils {

    public static void loadUrl(WebView webView, String url,Callback callback){
        //通过WebView的WebSetting类设置能够执行JavaScript的脚本
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

        webView.getSettings().setSupportZoom(true); //设置可以支持缩放
        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webView.getSettings().setBuiltInZoomControls(true);//设置出现缩放工具
        webView.setWebViewClient(new MyWebViewClient(callback));//设置用WebView打开内部链接

    }

    private static class MyWebViewClient extends WebViewClient {

        private Callback callback;
        public MyWebViewClient(Callback callback){
            this.callback = callback;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //点击二次打开链接使用当前的WebViwe,而不是调用系统的
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if(callback != null)
            callback.startLoading(url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(callback != null)
            callback.finishLoading(url);
        }
    }

    public interface Callback{
        void startLoading(String url);
        void finishLoading(String url);
    }
}
