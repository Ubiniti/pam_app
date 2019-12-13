package com.example.pam_app1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pam_app1.Entity.Note;
import com.example.pam_app1.Entity.User;
import com.example.pam_app1.ItemListener;
import com.example.pam_app1.NoteListAdapter;
import com.example.pam_app1.PAMDatabase;
import com.example.pam_app1.R;
import com.example.pam_app1.UserStorage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class NoteListActivity extends AppCompatActivity {
    private final int CREATE_NOTE_REQUEST = 1;
    private final int EDIT_NOTE_REQUEST = 2;

    private RecyclerView recyclerView;
    private NoteListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private FloatingActionButton btnAddNote;

    User user;
    ArrayList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        this.recyclerView = findViewById(R.id.rv_notes_list);
        this.btnAddNote = findViewById(R.id.btn_add_note);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        user = UserStorage.getLoggedInUser();

        this.notes = new ArrayList<Note>(
                Arrays.asList(PAMDatabase.getInstance(this).notesDao().findAllByUserIdSortByDate(user.uid))
        );

        mAdapter = new NoteListAdapter(notes);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ItemListener() {
            @Override
            public void onItemClick(int pos) {
                startNoteEditActivity(notes.get(pos));
            }
        });

        mAdapter.setOnDeleteBtnClickListener(new ItemListener() {
            @Override
            public void onItemClick(int pos) {
                deleteNote(notes.get(pos), pos);
            }
        });

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNoteEditActivity();
            }
        });
    }

    private void startNoteEditActivity(Note note) {
        Intent editNoteIntent = new Intent(this, NoteEditActivity.class);
        editNoteIntent.putExtra("note", (new Gson()).toJson(note));
        startActivityForResult(editNoteIntent, EDIT_NOTE_REQUEST);
    }

    private void startNoteEditActivity() {
        Intent editNoteIntent = new Intent(this, NoteEditActivity.class);
        startActivityForResult(editNoteIntent, CREATE_NOTE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_NOTE_REQUEST && resultCode == RESULT_OK && data != null) {
            String jsonData = data.getDataString();
            Note note = (new Gson()).fromJson(jsonData, Note.class);
            addNote(note);
        }

        if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK && data != null) {
            String jsonData = data.getDataString();
            Note note = (new Gson()).fromJson(jsonData, Note.class);
            updateNote(note);
        }
    }

    public void addNote(Note note) {
        this.notes.add(0, note);
        PAMDatabase.getInstance(this).notesDao().insertNote(note);
        mAdapter.notifyDataSetChanged();
    }

    public void updateNote(Note note) {
        for (int pos = 0; pos < notes.size(); pos++) {
            if (this.notes.get(pos).uid == note.uid) {
                this.notes.remove(pos);
                this.notes.add(pos, note);
                mAdapter.notifyItemChanged(pos);
                return;
            }
        }
        PAMDatabase.getInstance(this).notesDao().updateNote(note.uid, note.title, note.content);
    }

    public void deleteNote(Note note, int pos) {
        PAMDatabase.getInstance(this).notesDao().deleteNote(note.uid);
        notes.remove(pos);
        mAdapter.notifyDataSetChanged();
    }

    public void LogNote(Note note) {
        System.out.println(note.uid + " : " + note.title);
    }
}
