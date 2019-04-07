package com.zheng.zhi.campussystem.adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.activity.NoteBookActivity;
import com.zheng.zhi.campussystem.bean.NoteBook;
import com.zheng.zhi.campussystem.dialog.MenuNoteBookDialog;
import com.zheng.zhi.campussystem.utils.ToastUtils;

import java.util.List;

public class NoteBookAdapter extends RecyclerView.Adapter<NoteBookAdapter.NoteBookViewHolder> {

    private Context context;
    private List<NoteBook> noteBookList;
    private AppCompatActivity activity;
    private NoteBookActivity noteBookActivity;
    private MenuNoteBookDialog menuNoteBookDialog;
    private String serachString = "";

    public NoteBookAdapter(Context context, AppCompatActivity activity, List<NoteBook> noteBooks) {
        this.context = context;
        this.noteBookList = noteBooks;
        this.activity = activity;
        this.noteBookActivity = (NoteBookActivity) activity;
    }

    public void setShowList(List<NoteBook> noteBooks, String serachString) {
        this.noteBookList = noteBooks;
        this.serachString = serachString;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note_book, null);
        return new NoteBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteBookViewHolder holder, int position) {
        if (serachString.length() == 0) {
            holder.tvTitle.setText(noteBookList.get(position).getTitle());
            holder.tvContent.setText(noteBookList.get(position).getContent());
        } else {
            NoteBook noteBook = noteBookList.get(position);
            if (noteBook.getTitle().contains(serachString)) {
                try {
                    //主要用来用户搜索着色
                    SpannableString spannableString = new SpannableString(noteBook.getTitle());
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), noteBook.getTitle().indexOf(serachString),
                            noteBook.getTitle().indexOf(serachString) + serachString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    holder.tvTitle.setText(spannableString);
                } catch (Exception e) {

                }
            } else {
                try {
                    //主要用来用户搜索着色
                    SpannableString spannableString = new SpannableString(noteBook.getContent());
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), noteBook.getContent().indexOf(serachString),
                            noteBook.getContent().indexOf(serachString) + serachString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    holder.tvContent.setText(spannableString);
                } catch (Exception e) {

                }

            }
        }
        String time = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(noteBookList.get(position).getCreateTime());
        holder.tvTime.setText(time);
        holder.llNoteBook.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                menuNoteBookDialog = new MenuNoteBookDialog(serachString.length() > 0, noteBookList.get(position), new MenuNoteBookDialog.Callback() {
                    @Override
                    public void add() {
                        menuNoteBookDialog.dismiss();
                        menuNoteBookDialog = null;
                        noteBookActivity.showAddNoteBookDialog();
                    }

                    @Override
                    public void delete(NoteBook noteBook) {
                        menuNoteBookDialog.dismiss();
                        menuNoteBookDialog = null;
                        noteBookActivity.deleteNoteBook(noteBook);
                        ToastUtils.toast(context, "删除笔记成功!");
                    }

                    @Override
                    public void update(NoteBook noteBook) {
                        menuNoteBookDialog.dismiss();
                        menuNoteBookDialog = null;
                        noteBookActivity.showUpdateNoteBookDialog(noteBook);
                    }

                    @Override
                    public void serach() {
                        menuNoteBookDialog.dismiss();
                        menuNoteBookDialog = null;
                        noteBookActivity.serach();
                    }
                });
                menuNoteBookDialog.showNow(activity.getSupportFragmentManager(), "menu");
                return false;
            }
        });
        holder.llNoteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteBookActivity.showUpdateNoteBookDialog(noteBookList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteBookList == null ? 0 : noteBookList.size();
    }

    public static class NoteBookViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvContent, tvTime;
        LinearLayout llNoteBook;

        public NoteBookViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvTime = itemView.findViewById(R.id.tv_time);
            llNoteBook = itemView.findViewById(R.id.ll_note_book);
        }
    }

}
