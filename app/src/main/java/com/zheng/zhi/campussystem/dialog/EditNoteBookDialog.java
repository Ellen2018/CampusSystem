package com.zheng.zhi.campussystem.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.base.BaseDialogFragment;
import com.zheng.zhi.campussystem.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

//添加 & 修改笔记的对话框
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
    @BindView(R.id.tv_title)
    TextView tvTitleName;

    @OnClick({R.id.back,R.id.ok})
    void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                callback.cancel();
                break;
            case R.id.ok:
                //空字符串检测
                String title = etTitle.getText().toString().trim();
                String content = etContent.getText().toString().trim();
                if(TextUtils.isEmpty(title) || TextUtils.isEmpty(content)){
                    ToastUtils.toast(getActivity(),"备忘录标题或者内容不能为空");
                    return;
                }
                callback.ok(title,content,System.currentTimeMillis());
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
       etTitle.setText(title);
       etContent.setText(content);
       if(title != null && title.length() > 0){
           tvTitleName.setText("查看&修改");
       }
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
