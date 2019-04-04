package com.zheng.zhi.campussystem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zheng.zhi.campussystem.R;
import com.zheng.zhi.campussystem.bean.NoteBook;

import java.util.List;

public class NoteBookAdapter extends RecyclerView.Adapter<NoteBookAdapter.NoteBookViewHolder> {

    private Context context;
    private List<NoteBook> noteBookList;

    public NoteBookAdapter(Context context, List<NoteBook> noteBooks) {
        this.context = context;
        this.noteBookList = noteBooks;
    }


    @NonNull
    @Override
    public NoteBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note_book, null);
        return new NoteBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteBookViewHolder holder, int position) {
        holder.tvTitle.setText(noteBookList.get(position).getTitle());
        holder.tvContent.setText(noteBookList.get(position).getContent());
        String time = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(noteBookList.get(position).getCreateTime());
        holder.tvTime.setText(time);
    }

    @Override
    public int getItemCount() {
        return noteBookList == null ? 0 : noteBookList.size();
    }

    public static class NoteBookViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle,tvContent,tvTime;

        public NoteBookViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }

}
