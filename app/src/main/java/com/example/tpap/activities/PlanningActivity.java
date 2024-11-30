package com.example.tpap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tpap.R;
import com.example.tpap.fragments.TravelLocationFragment;
import com.example.tpap.states.BaseState;
import com.example.tpap.states.LocationState;
import com.example.tpap.view_models.TravelInfoViewModel;

public class PlanningActivity extends AppCompatActivity {
    enum FragState {location, date, style}
    FragState fragState = FragState.location;
    Button previous_button, next_button;
    ImageButton exit_button;
    TextView title_textView, subTitle_textView;

    RelativeLayout title_layout;
    RelativeLayout actionButton_layout;
    private TravelInfoViewModel travelInfoVM;

    public String travel_destination = "";
    public String travel_startDate = "";
    public String travel_endDate = "";
    public String travel_style = "";
    public String travel_fileName = "";

    BaseState currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        exit_button = findViewById(R.id.close_button);
        previous_button = findViewById(R.id.previous_button);
        next_button = findViewById(R.id.next_button);

        title_textView = findViewById(R.id.title_textView);
        subTitle_textView = findViewById(R.id.subTitle_textView);

        title_layout = findViewById(R.id.title_layout);
        actionButton_layout = findViewById(R.id.actionButton_layout);

        travelInfoVM = new ViewModelProvider(this).get(TravelInfoViewModel.class);

        currentState = new LocationState(this);
        currentState.updateUI();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_planning, new TravelLocationFragment()).commit();
        }

        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityTransaction(MainActivity.class);
            }
        });

        previous_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseState previousState = currentState.previousState();
                if (previousState != null)
                {
                    currentState = previousState;
                    currentState.updateUI();
                }
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseState nextState = currentState.nextState();
                if (nextState != null)
                {
                    currentState = nextState;
                    currentState.updateUI();
                }
            }
        });

        travelInfoVM.destination.observe(this, location -> {
            // location 데이터가 업데이트되면 Toast로 표시
            travel_destination = location;
            Toast.makeText(this, "선택된 여행지: " + travel_destination, Toast.LENGTH_SHORT).show();
        });
        travelInfoVM.startDate.observe(this, startDate -> {
            // location 데이터가 업데이트되면 Toast로 표시
            travel_startDate = startDate;
            Toast.makeText(this, "선택된 여행 시작 날짜: " + travel_startDate, Toast.LENGTH_SHORT).show();
        });
        travelInfoVM.endDate.observe(this, endDate -> {
            // location 데이터가 업데이트되면 Toast로 표시
            travel_endDate = endDate;
            Toast.makeText(this, "선택된 여행 종료 날짜: " + travel_endDate, Toast.LENGTH_SHORT).show();
        });
        travelInfoVM.style.observe(this, style -> {
            // location 데이터가 업데이트되면 Toast로 표시
            travel_style = style;
            Toast.makeText(this, "선택된 여행 스타일: " + travel_style, Toast.LENGTH_SHORT).show();
        });
        travelInfoVM.fileName.observe(this, fileName -> {
            // location 데이터가 업데이트되면 Toast로 표시
            travel_fileName = fileName;
            Toast.makeText(this, "선택된 파일 이름: " + travel_fileName, Toast.LENGTH_SHORT).show();
        });
    }
    public void resetVM(int i)
    {
        switch (i)
        {
            case 1:
                travelInfoVM.destination.setValue("");
                travelInfoVM.startDate.setValue("");
                travelInfoVM.endDate.setValue("");
                break;
            case 2:
                travelInfoVM.startDate.setValue("");
                travelInfoVM.endDate.setValue("");
                travelInfoVM.style.setValue("");
                break;
            case 3:
                travelInfoVM.style.setValue("");
                travelInfoVM.fileName.setValue("");
                break;
        }
    }
    public void fragmentTransaction(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_planning, fragment).commit();
    }
    public void activityTransaction(Class<?> activity)
    {
        Intent intent = new Intent(PlanningActivity.this, activity);
        startActivity(intent);
    }
    public void makeToast(String txt)
    {
        Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_SHORT).show();
    }
    public void setTitle(String title, String subtitle)
    {
        title_textView.setText(title);
        subTitle_textView.setText(subtitle);
    }
    public void setButtonText(String txt1, String txt2)
    {
        previous_button.setText(txt1);
        next_button.setText(txt2);
    }
    public void setFragmentFull()
    {
        title_layout.setVisibility(View.GONE);
        actionButton_layout.setVisibility(View.GONE);
    }
}