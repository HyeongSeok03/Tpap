package com.example.tpap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tpap.R;
import com.example.tpap.adapter.DayPlanAdapter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlanDisplayActivity extends AppCompatActivity {
    List<String> planList;
    List<HashMap<String, String>> dayPlans;

    String destination;
    String startDate;
    String endDate;
    int duration;

    ImageButton home_imageButton;
    TextView title_textView;
    TextView travel_period_textView;

    ListView plan_listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_display);

        // 파일 읽기
        String fileName = getIntent().getStringExtra("fileName");

        planList = new ArrayList<>();

        home_imageButton = findViewById(R.id.home_imageButton);
        title_textView = findViewById(R.id.title_textView);
        travel_period_textView = findViewById(R.id.travel_period_textView);
        plan_listView = findViewById(R.id.plan_listView);

        try {
            planList = readPlanFromInternalStorage(fileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        destination = planList.get(1);
        startDate = planList.get(3);
        endDate = planList.get(5);
        duration = Integer.parseInt(planList.get(7));

        title_textView.setText(String.format("Trip to %s", destination));
        travel_period_textView.setText(String.format("%s ~ %s", startDate, endDate));

        prepareDayPlans();

        DayPlanAdapter adapter = new DayPlanAdapter(this, dayPlans);
        plan_listView.setAdapter(adapter);

        home_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanDisplayActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<String> readPlanFromInternalStorage(String fileName) throws Exception {
        StringBuilder content = new StringBuilder();
        List<String> travelPlanList = new ArrayList<>();


        try (FileInputStream fis = openFileInput(fileName);

             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            String[] planArray = content.toString().split("\\*");
            for (String part : planArray) {
                travelPlanList.add(part.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return travelPlanList;
    }

    private void prepareDayPlans() {
        dayPlans = new ArrayList<>();

        for (int i = 1; i <= duration; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("day", planList.get(i*11-2));
            map.put("morning", planList.get(i*11));
            map.put("lunch", planList.get(i*11+2));
            map.put("afternoon", planList.get(i*11+4));
            map.put("dinner", planList.get(i*11+6));
            map.put("night", planList.get(i*11+8));
            dayPlans.add(map);
        }
    }
}
