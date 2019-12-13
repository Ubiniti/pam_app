package com.example.pam_app1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pam_app1.Entity.Note;
import com.example.pam_app1.R;
import com.example.pam_app1.UserStorage;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NoteEditActivity extends AppCompatActivity {
    TextView note_title;
    TextView note_content;
    TextView note_date;
    TextView note_locality;
    EditText note_title_edit;
    EditText note_content_edit;
    LinearLayout note_preview_layout;
    LinearLayout note_edit_layout;
    Button btn_save_note;

    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        this.note_title = findViewById(R.id.note_title);
        this.note_content = findViewById(R.id.note_content);
        this.note_date = findViewById(R.id.note_date);
        this.note_locality = findViewById(R.id.note_locality);
        this.note_title_edit = findViewById(R.id.note_title_edit);
        this.note_content_edit = findViewById(R.id.note_content_edit);
        this.note_preview_layout = findViewById(R.id.note_preview_layout);
        this.note_edit_layout = findViewById(R.id.note_edit_layout);
        this.btn_save_note = findViewById(R.id.btn_save_note);

        btn_save_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitActivityResult();
            }
        });

        this.note = getNote(getIntent());
        updateForm(this.note);
    }

    private void submitActivityResult() {
        uploadDataToModel();
        Intent result = new Intent();
        String jsonData = (new Gson()).toJson(this.note);
        result.setData(Uri.parse(jsonData));
        setResult(RESULT_OK, result);
        finish();
    }

    private void uploadDataToModel() {
        note.title = note_title_edit.getText().toString();
        note.content = note_content_edit.getText().toString();
        note.updatedAt = (new SimpleDateFormat(Note.DATE_FORMAT)).format(
                Calendar.getInstance().getTime()
        );
        note.userId = UserStorage.getLoggedInUser().uid;
    }

    private void updateForm(Note note) {
        note_title_edit.setText(note.title);
        note_content_edit.setText(note.content);
    }

    private Note getNote(Intent intent) {
        String jsonData = intent.getStringExtra("note");
        if (jsonData == null) {
            return new Note();
        }

        return (new Gson()).fromJson(jsonData, Note.class);
    }
}
