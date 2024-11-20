package com.example.tpap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.tpap.R;
import com.example.tpap.fragments.TravelDateFragment;
import com.example.tpap.fragments.TravelLocationFragment;
import com.example.tpap.fragments.TravelStyleFragment;
import com.example.tpap.view_models.TravelInfo;

public class PreplanActivity extends AppCompatActivity {
    enum FragState {location, date, style}
    FragState fragState = FragState.location;
    Button exit_button, previous_button, next_button;
    TextView title_textView;

    private TravelInfo travelInfo;

    String travel_destination = "";
    String travel_date = "test";
    String travel_style = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preplan);

        exit_button = findViewById(R.id.exit_button);
        previous_button = findViewById(R.id.cancel_button);
        next_button = findViewById(R.id.next_button);

        title_textView = findViewById(R.id.title_textView);

        travelInfo = new ViewModelProvider(this).get(TravelInfo.class);

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
                        fragState = FragState.location;
                        break;
                    case style:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_preplan, new TravelDateFragment()).commit();
                        title_textView.setText("When");
                        fragState = FragState.date;
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
                        if (travel_destination.isEmpty())
                        {
                            Toast.makeText(getApplicationContext(), "You should select your destination.", Toast.LENGTH_SHORT).show();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_preplan, new TravelDateFragment()).commit();
                        title_textView.setText("When");
                        fragState = FragState.date;
                        break;
                    case date:
                        if (travel_date.isEmpty())
                        {
                            Toast.makeText(getApplicationContext(), "You should select your travel dates.", Toast.LENGTH_SHORT).show();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_preplan, new TravelStyleFragment()).commit();
                        title_textView.setText("How");
                        fragState = FragState.style;
                        break;
                    case style:
                        if (travel_style.isEmpty())
                        {
                            Toast.makeText(getApplicationContext(), "You should select your travel style.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });

        travelInfo.location.observe(this, location -> {
            // location 데이터가 업데이트되면 Toast로 표시
            travel_destination = location;
            Toast.makeText(this, "선택된 여행지: " + travel_destination, Toast.LENGTH_SHORT).show();
        });
    }
}