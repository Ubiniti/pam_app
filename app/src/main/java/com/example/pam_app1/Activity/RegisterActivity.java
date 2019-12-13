package com.example.pam_app1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pam_app1.Entity.User;
import com.example.pam_app1.PAMDatabase;
import com.example.pam_app1.R;

public class RegisterActivity extends AppCompatActivity {

    static final int MIN_CHARS = 6;

    PAMDatabase database;

    RegisterActivity activity;

    EditText cUsername, cPassword, cRepeatPassword;

    Button cRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.activity = this;

        this.database = PAMDatabase.getInstance(getApplicationContext());

        this.cUsername = findViewById(R.id.input_new_username);
        this.cPassword = findViewById(R.id.input_new_password);
        this.cRepeatPassword = findViewById(R.id.input_repeat_password);
        this.cRegister = findViewById(R.id.register);

        cRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = cUsername.getText().toString();
                String password = cPassword.getText().toString();
                String repeatPassword = cRepeatPassword.getText().toString();

                if (!validPassword(password, repeatPassword)) {
                    return;
                }

                User user = new User();
                user.username = username;
                user.password = password;

                if (userExists(username)) {
                    return;
                }

                database.userDao().registerUser(user);

                returnToLoginActivity();
            }
        });
    }

    private boolean userExists(String username) {
        User user = database.userDao().findOneByUsername(username);
        if (user != null) {
            showToastMessage("Uzytkownik z takim loginem już instnieje");

            return true;
        }

        return false;
    }

    private boolean validPassword(String password, String repeatPassword) {
        if (!password.equals(repeatPassword)) {
            showToastMessage("Podane hasła nie są identyczne");

            return false;
        }

        if (password.length() < MIN_CHARS) {
            showToastMessage("Hasło musi zawierać co najmniej 6 znaków");
            return false;
        }

        return true;
    }

    public void showToastMessage(String message)
    {
        Toast.makeText(getApplicationContext(),
                message, Toast.LENGTH_SHORT).show();
    }

    private void returnToLoginActivity() {

        Intent result = new Intent();
        String username = cUsername.getText().toString();
        result.setData(Uri.parse(username));
        setResult(RESULT_OK, result);
        finish();
    }
}
