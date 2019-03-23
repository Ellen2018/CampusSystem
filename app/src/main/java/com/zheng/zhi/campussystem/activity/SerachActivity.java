package com.zheng.zhi.campussystem.activity;

import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.base.BaseActivity;
import com.zheng.zhi.campussystem.helper.MyMMKV;
import com.zheng.zhi.campussystem.utils.ToastUtils;
import com.zheng.zhi.campussystem.utils.WebViewSetttingUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SerachActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_serach)
    EditText editText;
    @BindView(R.id.web_view)
    WebView webView;

    private String serachUrl = "http://www.qcuwh.cn/index.php/commonform-search.html?key=s1&button=+";
    private MyMMKV serachMmkv;
    private List<Serach> serachList;

    @OnClick({R.id.back,R.id.iv_serach})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_serach:
                String serachString = editText.getText().toString().trim();
                if(serachString.length() == 0){
                    ToastUtils.toast(context,"亲,搜索内容不能为空!");
                    return;
                }
                handlerSerach(serachString);
                WebViewSetttingUtils.loadUrl(webView, serachUrl.replace("s1",serachString), new WebViewSetttingUtils.Callback() {
                    @Override
                    public void startLoading(String url) {

                    }

                    @Override
                    public void finishLoading(String url) {

                    }
                });
                break;
        }
    }

    private void handlerSerach(String serachString) {
        boolean isAdd = false;
        for(Serach serach:serachList){
            if(serach.getContent().equals(serachString)){
                isAdd = true;
                serach.setCount(serach.getCount()+1);
                break;
            }
        }
        if(!isAdd){
            Serach serach = new Serach();
            serach.setContent(serachString);
            serach.setCount(1);
            serachList.add(serach);
        }
        Gson gson = new Gson();
        String newJson = gson.toJson(serachList);
        serachMmkv.save(serachUrl,newJson);
        Log.e("新的Json数据",newJson);
    }

    @Override
    protected void setStatus() {

    }

    @Override
    protected Boolean isSetVerticalScreen() {
        return null;
    }

    @Override
    protected void initData() {
        tvTitle.setText("站内搜索");
        serachMmkv = new MyMMKV(this.getClass().getName());
        Gson gson = new Gson();
        String json = (String) serachMmkv.getValue(serachUrl,"");
        if(json.length() > 0 ) {
            serachList = getSerachList(json);
        }else {
            serachList = new ArrayList<>();
        }
    }

    private List<Serach> getSerachList(String json){
        Type type = new TypeToken<ArrayList<Serach>>()
        {}.getType();
        return new Gson().fromJson(json, type);
    }

    @Override
    protected void initView() {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    //获取了焦点
                }else {
                    //失去了焦点
                }
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_serach;
    }

    private static  class Serach{
        private String content;
        private int count;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

}
