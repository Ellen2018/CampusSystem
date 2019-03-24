package com.zheng.zhi.campussystem.activity;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.base.BaseActivity;
import com.zheng.zhi.campussystem.base.BasePopWindow;
import com.zheng.zhi.campussystem.dialog.SerachHistoryDialog;
import com.zheng.zhi.campussystem.helper.MyMMKV;
import com.zheng.zhi.campussystem.utils.ToastUtils;
import com.zheng.zhi.campussystem.utils.WebViewSetttingUtils;
import com.zheng.zhi.campussystem.utils.statusutil.StatusUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
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
    private SerachHistoryDialog serachHistoryDialog;

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
                toSerach(serachString);
                break;
        }
    }

    private void toSerach(String serachString){
        handlerSerach(serachString);
        WebViewSetttingUtils.loadUrl(webView, serachUrl.replace("s1",serachString), new WebViewSetttingUtils.Callback() {
            @Override
            public void startLoading(String url) {

            }

            @Override
            public void finishLoading(String url) {

            }
        });
    }

    private void handlerSerach(String serachString) {
        boolean isAdd = false;
        Serach s = null;
        for(Serach serach:serachList){
            if(serach.getContent().equals(serachString)){
                isAdd = true;
                s = serach;
                serach.setCount(serach.getCount()+1);
                serach.setSerachDate((new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(new Date()));
                break;
            }
        }
        if(!isAdd){
            Serach serach = new Serach();
            serach.setContent(serachString);
            serach.setCount(1);
            serach.setSerachDate((new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(new Date()));
            if(serachList.size() > 1){
                Serach zeroSerach = serachList.get(0);
                serachList.set(0,serach);
                serachList.add(zeroSerach);
            }else {
                serachList.add(serach);
            }
        }else {
            serachList.remove(s);
            serachList.add(0,s);
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
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                editText.requestFocusFromTouch();
                showSerachDialog();
            }
        });
       editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean b) {
               if(b){
                   showSerachDialog();
               }else {
                   cancelDialog();
               }
           }
       });
    }

    private void showSerachDialog(){
        if(serachHistoryDialog == null) {
            serachHistoryDialog = new SerachHistoryDialog(context, SerachActivity.this, editText, serachList, new SerachHistoryDialog.Callback() {
                @Override
                public void serach(String content) {
                    toSerach(content);
                    editText.setText(content);
                    cancelDialog();
                }
            });
            serachHistoryDialog.showBottom();
            serachHistoryDialog.setOnDismissListener(new BasePopWindow.OnDismissListener() {
                @Override
                public void dissmiss() {
                    serachHistoryDialog = null;
                }
            });
        }
    }

    private void cancelDialog(){
        serachHistoryDialog.dismiss();
        serachHistoryDialog = null;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_serach;
    }

    public static  class Serach{
        private String content;
        private int count;
        private String serachDate;

        public String getSerachDate() {
            return serachDate;
        }

        public void setSerachDate(String serachDate) {
            this.serachDate = serachDate;
        }

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
