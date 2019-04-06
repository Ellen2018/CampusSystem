package com.zheng.zhi.campussystem.activity;

import android.opengl.Visibility;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.adapter.NoteBookAdapter;
import com.zheng.zhi.campussystem.base.BaseActivity;
import com.zheng.zhi.campussystem.bean.NoteBook;
import com.zheng.zhi.campussystem.dialog.EditNoteBookDialog;
import com.zheng.zhi.campussystem.helper.MyMMKV;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoteBookActivity extends BaseActivity implements BaseActivity.ButterKnifeInterface {

    private List<NoteBook> noteBookList;
    private MyMMKV myNoteBookMMKV;
    private EditNoteBookDialog editNoteBookDialog;
    private NoteBookAdapter noteBookAdapter;
    private List<NoteBook> serachNoteBookList;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.rl_title_bar)
    RelativeLayout rlTitleBar;
    @BindView(R.id.ll_serach)
    LinearLayout llSerach;
    @BindView(R.id.et_serach)
    EditText etSerach;

    @OnClick({R.id.back, R.id.serach, R.id.iv_add_note_book,R.id.iv_serach_cancel})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.serach:
                rlTitleBar.setVisibility(View.GONE);
                llSerach.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_add_note_book:
                showAddNoteBookDialog();
                break;
            case R.id.iv_serach_cancel:
                etSerach.setText("");
                rlTitleBar.setVisibility(View.VISIBLE);
                llSerach.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                if(noteBookList == null || noteBookList.size() == 0){
                    tvNoData.setText(getString(R.string.no_data));
                    tvNoData.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }else {
                    recyclerView.setVisibility(View.VISIBLE);
                    tvNoData.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void saveNoteBook(List<NoteBook> noteBooks) {
        tvNoData.setVisibility(View.GONE);
        Gson gson = new Gson();
        String json = gson.toJson(noteBooks);
        myNoteBookMMKV.save(this.getClass().getName(), json);
    }

    //删除笔记
    public void deleteNoteBook(NoteBook noteBook) {
        noteBookList.remove(noteBook);
        saveNoteBook(noteBookList);
        noteBookAdapter.notifyDataSetChanged();
        if (noteBookList == null || noteBookList.size() == 0) {
            tvNoData.setText(getString(R.string.no_data));
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            tvNoData.setVisibility(View.GONE);
        }

        if(serachNoteBookList != null) {
            serachNoteBookList.remove(noteBook);
            if (serachNoteBookList == null || serachNoteBookList.size() == 0) {
                tvNoData.setText("无[" + etSerach.getText().toString().trim() + "]匹配到的数据!");
                tvNoData.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
    }

    //修改笔记
    public void showUpdateNoteBookDialog(NoteBook noteBook) {
        editNoteBookDialog = new EditNoteBookDialog(noteBook.getTitle(), noteBook.getContent(), new EditNoteBookDialog.Callback() {
            @Override
            public void ok(String title, String content, long dateTime) {
                noteBook.setTitle(title);
                noteBook.setContent(content);
                noteBook.setCreateTime(dateTime);
                noteBookAdapter.notifyDataSetChanged();
                editNoteBookDialog.dismiss();
                editNoteBookDialog = null;
                saveNoteBook(noteBookList);
                if(llSerach.getVisibility() == View.VISIBLE) {
                    serach(etSerach.getText().toString());
                }
            }

            @Override
            public void cancel() {
                editNoteBookDialog.dismiss();
                editNoteBookDialog = null;
            }
        });
        editNoteBookDialog.show(getSupportFragmentManager(), "编辑文本");
    }

    @Override
    protected void setStatus() {

    }

    public void showAddNoteBookDialog() {
        editNoteBookDialog = new EditNoteBookDialog(new EditNoteBookDialog.Callback() {
            @Override
            public void ok(String title, String content, long dateTime) {
                editNoteBookDialog.dismiss();
                editNoteBookDialog = null;
                NoteBook noteBook = new NoteBook(title, content, dateTime);
                noteBookList.add(0, noteBook);
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
        editNoteBookDialog.show(getSupportFragmentManager(), "编辑文本");
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_notebook;
    }

    @Override
    protected void initView() {
        myNoteBookMMKV = new MyMMKV(this.getClass().getName());
        String json = (String) myNoteBookMMKV.getValue(this.getClass().getName(), "");
        Log.e("获取Json",json);
        if (json == null || json.length() == 0 || json.equals(("[]"))) {
            noteBookList = new ArrayList<>();
            tvNoData.setText(getString(R.string.no_data));
            tvNoData.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NoteBookActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            noteBookAdapter = new NoteBookAdapter(this, NoteBookActivity.this, noteBookList);
            recyclerView.setAdapter(noteBookAdapter);
        } else {
            Gson gson = new Gson();
            noteBookList = gson.fromJson(json, new TypeToken<List<NoteBook>>() {
            }.getType());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NoteBookActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            noteBookAdapter = new NoteBookAdapter(this, NoteBookActivity.this, noteBookList);
            recyclerView.setAdapter(noteBookAdapter);
        }

        etSerach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //进行查找
                serach(editable.toString().trim());
            }
        });
    }

    private void serach(String serachString){
        serachNoteBookList = new ArrayList<>();
        for(NoteBook noteBook:noteBookList){
            if(noteBook.getTitle().contains(serachString) || noteBook.getContent().contains(serachString)){
                serachNoteBookList.add(noteBook);
            }
        }
        if(serachNoteBookList.size() == 0){
            //无匹配数据
            noteBookAdapter.setShowList(noteBookList,serachString);
            tvNoData.setText("无["+serachString+"]匹配到的数据!");
            tvNoData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            noteBookAdapter.setShowList(serachNoteBookList,serachString);
            tvNoData.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
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
