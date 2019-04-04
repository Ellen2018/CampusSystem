package com.zheng.zhi.campussystem.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.base.BaseDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class EditNoteBookDialog extends BaseDialogFragment implements BaseDialogFragment.ButterKnifeInterface {

    private Unbinder unbinder;
    private Callback callback;
    private String title;
    private String content;

    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;

    @OnClick({R.id.back,R.id.ok})
    void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                callback.cancel();
                break;
            case R.id.ok:
                callback.ok(etTitle.getText().toString().trim(),etContent.getText().toString().trim(),System.currentTimeMillis());
                break;
        }
    }

    public EditNoteBookDialog(Callback callback){
        this.callback = callback;
    }

    public EditNoteBookDialog(String title,String content,Callback callback){
        this.callback = callback;
        this.title = title;
        this.content = content;
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_edit_note_book;
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
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void unBindButterKnife() {
        unbinder.unbind();
    }

    public interface Callback{
        void ok(String title,String content,long dateTime);
        void cancel();
    }
}
