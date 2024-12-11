package com.example.tpap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tpap.R;

public class LoginActivity extends AppCompatActivity {
    Button login_button;
    EditText username_editText;
    EditText password_editText;
    ImageButton visibilityToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_button = findViewById(R.id.login_button);
        username_editText = findViewById(R.id.username_editText);
        password_editText = findViewById(R.id.password_editText);
        visibilityToggle = findViewById(R.id.visibility_toggle);

        login_button.setOnClickListener(v -> {
            String username = username_editText.getText().toString();
            String password = password_editText.getText().toString();
            if (username.equals("qwer") && password.equals("qwer"))
            {
                Log.i("Transition", "LoginActivity -> MainActivity");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else
            {
                Toast toast = Toast.makeText(LoginActivity.this, "Both id and password is 'qwer'.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

        visibilityToggle.setOnClickListener( v -> {
            int type = password_editText.getInputType();
            Log.e("check", String.valueOf(username_editText.getInputType()));
            if (type == 129)
            {
                password_editText.setInputType(1);
                password_editText.setSelection(password_editText.getText().length()); // 커서 유지
                visibilityToggle.setSelected(true);
            }
            else
            {
                password_editText.setInputType(129);
                password_editText.setSelection(password_editText.getText().length()); // 커서 유지
                visibilityToggle.setSelected(false);
            }
        });
    }
}