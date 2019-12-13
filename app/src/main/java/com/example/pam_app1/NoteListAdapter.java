package com.example.pam_app1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pam_app1.Entity.Note;

import java.util.ArrayList;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteListViewHolder> {
    private ArrayList<Note> notes;
    private ItemListener itemListener;
    private ItemListener deleteItemListener;

    static class NoteListViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView title;
        TextView content;
        TextView date;
        TextView locality;
        AppCompatImageButton btnDelete;

        NoteListViewHolder(LinearLayout view) {
            super(view);

            this.layout = view;
            this.title = view.findViewById(R.id.label_note_title);
            this.content = view.findViewById(R.id.label_note_content);
            this.date = view.findViewById(R.id.label_note_date);
            this.locality = view.findViewById(R.id.label_note_locality);
            this.btnDelete = view.findViewById(R.id.btn_note_delete);
        }
    }

    public NoteListAdapter(ArrayList<Note> notes) {
        this.notes = notes;
    }

    @Override
    @NonNull
    public NoteListAdapter.NoteListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout noteLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_list_item, parent, false);

        return new NoteListViewHolder(noteLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListViewHolder holder, final int position) {
        holder.title.setText(notes.get(position).title);
        holder.content.setText(notes.get(position).content);
        holder.date.setText(notes.get(position).updatedAt);
        holder.locality.setText(notes.get(position).location);

        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.onItemClick(position);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItemListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setOnItemClickListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setOnDeleteBtnClickListener(ItemListener itemListener) {
        this.deleteItemListener = itemListener;
    }
}
