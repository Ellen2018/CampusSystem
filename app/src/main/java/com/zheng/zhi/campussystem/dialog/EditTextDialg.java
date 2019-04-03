package com.zheng.zhi.campussystem.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.base.BaseDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class EditTextDialg extends BaseDialogFragment implements BaseDialogFragment.ButterKnifeInterface {

    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.et_content)
    EditText etContent;

    @OnClick({R.id.cancel,R.id.bt_cancel,R.id.bt_ok})
    void onClick(View view){
        switch (view.getId()){
            case R.id.cancel:
                dismiss();
                break;
            case R.id.bt_cancel:
                dismiss();
                break;
            case R.id.bt_ok:
                callback.ok(etContent.getText().toString().trim());
                break;
        }
    }

    private String content = "";
    private String titleName;
    private Callback callback;
    private Unbinder unbinder;

    public EditTextDialg(String content,String titleName,Callback callback){
        this.content = content;
        this.titleName = titleName;
        this.callback = callback;
    }

    @Override
    protected void initData() {
        tvTitleName.setText(titleName);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_edit_text;
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
        unbinder = ButterKnife.bind(this,view);
    }

    @Override
    public void unBindButterKnife() {
        unbinder.unbind();
    }

    public interface Callback{
        void ok(String content);
    }
}
