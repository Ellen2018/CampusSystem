package com.zheng.zhi.campussystem.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.adapter.NoteBookAdapter;
import com.zheng.zhi.campussystem.base.BaseActivity;
import com.zheng.zhi.campussystem.bean.NoteBook;
import com.zheng.zhi.campussystem.dialog.EditNoteBookDialog;
import com.zheng.zhi.campussystem.helper.MyMMKV;
import com.zheng.zhi.campussystem.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoteBookActivity extends BaseActivity implements BaseActivity.ButterKnifeInterface{

    private List<NoteBook> noteBookList;
    private MyMMKV myNoteBookMMKV;
    private EditNoteBookDialog editNoteBookDialog;
    private NoteBookAdapter noteBookAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @OnClick({R.id.back,R.id.serach,R.id.iv_add_note_book})
    void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.serach:
                ToastUtils.toast(NoteBookActivity.this,"搜索");
                break;
            case R.id.iv_add_note_book:
                 editNoteBookDialog = new EditNoteBookDialog(new EditNoteBookDialog.Callback() {
                    @Override
                    public void ok(String title, String content, long dateTime) {
                        editNoteBookDialog.dismiss();
                        editNoteBookDialog = null;
                        NoteBook noteBook = new NoteBook(title,content,dateTime);
                        noteBookList.add(0,noteBook);
                        saveNoteBook(noteBookList);
                        //显示新的数据
                        noteBookAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void cancel() {
                        editNoteBookDialog.dismiss();
                        editNoteBookDialog = null;
                    }
                });
                editNoteBookDialog.show(getSupportFragmentManager(),"编辑文本");
                break;
        }
    }

    private void saveNoteBook(List<NoteBook> noteBooks){
        Gson gson = new Gson();
        String json = gson.toJson(noteBooks);
        myNoteBookMMKV.save(this.getClass().getName(),json);
    }


    @Override
    protected void setStatus() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_notebook;
    }

    @Override
    protected void initView() {
       myNoteBookMMKV = new MyMMKV(this.getClass().getName());
        String json = (String) myNoteBookMMKV.getValue(this.getClass().getName(),"");
        Log.e("存储的笔记数据",json);
        if(json == null || json.length() == 0){
            noteBookList  = new ArrayList<>();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NoteBookActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            noteBookAdapter = new NoteBookAdapter(NoteBookActivity.this,noteBookList);
            recyclerView.setAdapter(noteBookAdapter);
        }else {
            Gson gson = new Gson();
            noteBookList = gson.fromJson(json, new TypeToken<List<NoteBook>>() {
            }.getType());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NoteBookActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            noteBookAdapter = new NoteBookAdapter(NoteBookActivity.this,noteBookList);
            recyclerView.setAdapter(noteBookAdapter);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void destory() {

    }

    @Override
    protected Boolean isSetVerticalScreen() {
        return null;
    }

    @Override
    public void initButterKnife() {
        ButterKnife.bind(this);
    }
}
