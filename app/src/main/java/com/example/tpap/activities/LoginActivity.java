package com.example.tpap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tpap.R;

public class LoginActivity extends AppCompatActivity {
    Button login_button;
    EditText username_editText;
    EditText password_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_button = findViewById(R.id.login_button);
        username_editText = findViewById(R.id.username_editText);
        password_editText = findViewById(R.id.password_editText);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = username_editText.getText().toString();
                String password = password_editText.getText().toString();
                if (username.equals("qwer") && password.equals("qwer"))
                {
                    Log.i("Transition", "LoginActivity -> MainActivity");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}