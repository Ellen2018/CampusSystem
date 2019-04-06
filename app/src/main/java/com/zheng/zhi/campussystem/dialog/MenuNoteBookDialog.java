package com.zheng.zhi.campussystem.dialog;

import android.annotation.SuppressLint;
import android.view.View;

import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.base.BaseDialogFragment;
import com.zheng.zhi.campussystem.bean.NoteBook;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class MenuNoteBookDialog extends BaseDialogFragment implements BaseDialogFragment.ButterKnifeInterface {

    private Unbinder unbinder;

    private Callback callback;
    private NoteBook noteBook;

    @OnClick({R.id.cancel,R.id.tv_add_note_book,R.id.tv_delete_note_book,R.id.tv_update_note_book,R.id.tv_serach_note_book})
    void onClick(View view){
        switch (view.getId()){
            case R.id.cancel:
                dismiss();
                break;
            case R.id.tv_add_note_book:
                callback.add();
                break;
            case R.id.tv_delete_note_book:
                callback.delete(noteBook);
                break;
            case R.id.tv_update_note_book:
                callback.update(noteBook);
                break;
            case R.id.tv_serach_note_book:
                callback.serach();
                break;
        }
    }

    public MenuNoteBookDialog(NoteBook noteBook,Callback callback){
        this.callback = callback;
        this.noteBook = noteBook;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_note_book_menu;
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

    public interface  Callback{
        void add();
        void delete(NoteBook noteBook);
        void update(NoteBook noteBook);
        void serach();
    }

}
