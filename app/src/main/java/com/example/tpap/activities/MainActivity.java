package com.example.tpap.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.tpap.R;
import com.example.tpap.fragments.MainAccountFragment;
import com.example.tpap.fragments.MainHomeFragment;

public class MainActivity extends AppCompatActivity {
    ImageButton home_button, account_button, plus_button;
    Button planStart_button;
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

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new MainHomeFragment()).commit();
        }

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_button.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                account_button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C8C8C8")));
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new MainHomeFragment()).commit();
            }
        });
        account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account_button.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                home_button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C8C8C8")));
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new MainAccountFragment()).commit();
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
                Intent intent = new Intent(MainActivity.this, PlanningActivity.class);
                startActivity(intent);
            }
        });
    }
}