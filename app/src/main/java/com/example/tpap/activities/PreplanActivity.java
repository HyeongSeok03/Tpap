package com.example.tpap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tpap.R;
import com.example.tpap.fragments.TravelDateFragment;
import com.example.tpap.fragments.TravelLocationFragment;
import com.example.tpap.fragments.TravelStyleFragment;

public class PreplanActivity extends AppCompatActivity {
    enum FragState {location, date, style}
    FragState fragState = FragState.location;
    Button exit_button, previous_button, next_button;
    TextView title_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preplan);

        exit_button = findViewById(R.id.exit_button);
        previous_button = findViewById(R.id.cancel_button);
        next_button = findViewById(R.id.next_button);

        title_textView = findViewById(R.id.title_textView);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_preplan, new TravelLocationFragment()).commit();
        }

        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreplanActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        previous_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (fragState)
                {
                    case location:
                        intent = new Intent(PreplanActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case date:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_preplan, new TravelLocationFragment()).commit();
                        title_textView.setText("Where");
                        break;
                    case style:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_preplan, new TravelDateFragment()).commit();
                        title_textView.setText("When");
                        break;
                }
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (fragState)
                {
                    case location:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_preplan, new TravelDateFragment()).commit();
                        title_textView.setText("When");
                        break;
                    case date:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_preplan, new TravelStyleFragment()).commit();
                        title_textView.setText("How");
                        break;
                    case style:
                        break;
                }
            }
        });
    }
}