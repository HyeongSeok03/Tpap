package com.example.tpap.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.tpap.R;

public class MainActivity extends AppCompatActivity {
    Button home_button, account_button, plus_button, planStart_button;
    ImageButton background_button;
    ConstraintLayout constraint_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home_button = findViewById(R.id.home_button);
        account_button = findViewById(R.id.account_button);
        plus_button = findViewById(R.id.plus_button);

        constraint_layout = findViewById(R.id.planStart_viewGroup);
        background_button = findViewById(R.id.background_button);

        planStart_button = findViewById(R.id.planStart_button);

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraint_layout.setVisibility(View.VISIBLE);
            }
        });
        background_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraint_layout.setVisibility(View.GONE);
            }
        });
        planStart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}