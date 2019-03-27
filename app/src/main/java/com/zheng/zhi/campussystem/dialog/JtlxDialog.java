package com.zheng.zhi.campussystem.dialog;

import android.view.View;
import android.widget.TextView;

import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.base.BaseDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class JtlxDialog extends BaseDialogFragment implements BaseDialogFragment.ButterKnifeInterface{

    private Unbinder unbinder;

    private String content = "武汉大学珞珈学院新校园位于湖北省武汉市东湖高新技术开发区中华科技产业园玉屏大道9号，驾车可导航武汉晴川学院为终点站即可到达。\n" +
            "\n" +
            "在珞瑜路光谷广场乘坐922、909公交可直达 。\n" +
            "\n" +
            "乘车路线：\n" +
            "\n" +
            "1、在武昌火车站或武汉火车站乘地铁4号线到中南路站或洪山广场站换乘2号线至光谷广场站，在珞瑜路光谷广场站转乘922路公交或在珞瑜路鲁巷站转乘909路公交至兴龙街龙泉站（武汉晴川学院）；\n" +
            "\n" +
            "2、在汉口火车站乘地铁2号线至光谷广场站，在珞瑜路光谷广场站转乘922路公交或在珞瑜路鲁巷站转乘909路公交至兴龙街龙泉站（武汉晴川学院）。\n" +
            "\n" +
            "3、自驾车导航武汉晴川学院即可到达。";

    @BindView(R.id.tv_content)
    TextView tvContent;

    @OnClick(R.id.cancel)
    void onClick(View view){
        switch (view.getId()){
            case R.id.cancel:
                this.dismiss();
                break;
        }
    }

    @Override
    protected void initData() {
          tvContent.setText(content);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_jtlx;
    }

    @Override
    protected Boolean setCancelable() {
        return null;
    }

    @Override
    protected Boolean setCanceledOnTouchOutside() {
        return null;
    }

    @Override
    protected Boolean setWinowTransparent() {
        return null;
    }

    @Override
    public void initButterKnife(View view) {
       unbinder =  ButterKnife.bind(this,view);
    }

    @Override
    public void unBindButterKnife() {
           unbinder.unbind();
    }
}
