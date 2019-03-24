package com.zheng.zhi.campussystem.dialog;

import android.view.View;

import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.base.BaseDialogFragment;

import butterknife.OnClick;

public class LxwmDialog extends BaseDialogFragment {

    @OnClick(R.id.cancel)
    void onClick(View view){
        switch (view.getId()){
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_lxwm;
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
}
