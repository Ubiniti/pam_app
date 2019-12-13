package com.example.pam_app1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pam_app1.PAMDatabase;
import com.example.pam_app1.R;
import com.example.pam_app1.Entity.User;
import com.example.pam_app1.UserStorage;

public class MainActivity extends AppCompatActivity {

    static final int REGISTER_USER_REQUEST = 1;

    PAMDatabase database;

    MainActivity activity;

    EditText cUsername, cPassword;

    Button cSubmit, cRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.activity = this;

        this.database = PAMDatabase.getInstance(getApplicationContext());

        this.cUsername = findViewById(R.id.input_username);
        this.cPassword = findViewById(R.id.input_password);
        this.cSubmit = findViewById(R.id.submit);
        this.cRegister = findViewById(R.id.linkbtn_register);

        this.cSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = cUsername.getText().toString();
                String password = cPassword.getText().toString();

                User user = new User();
                user.username = username;
                user.password = password;

                User userFound = database.userDao().findOneWithCredentials(username, password);

                if (userFound != null) {
                    System.out.println(userFound.uid + ": " + userFound.username + " " + userFound.password);
                    showToastMessage("Login success");

                    switchToNoteListActivity(userFound);
                } else {
                    showToastMessage("Wrong credentials");
                }
            }
        });

        this.cRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegisterUserActivity();
            }
        });
    }

    private void switchToNoteListActivity(User user) {
        UserStorage.setUser(user);
        Intent noteListIntent = new Intent(this, NoteListActivity.class);
        startActivity(noteListIntent);
    }

    private void startRegisterUserActivity() {
        Intent registerUserIntent = new Intent(this, RegisterActivity.class);
        startActivityForResult(registerUserIntent, REGISTER_USER_REQUEST);
    }

    private void showToastMessage(String message)
    {
        Toast.makeText(getApplicationContext(),
                message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REGISTER_USER_REQUEST && resultCode == RESULT_OK && data != null) {
            String username = data.getDataString();
            cUsername.setText(username);
            cPassword.requestFocus();
        }
    }
}
