package com.example.tpap.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.tpap.R;
import com.example.tpap.activities.PlanDisplayActivity;
import com.example.tpap.adapter.PlanFileAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainHomeFragment extends Fragment {

    private List<HashMap<String, String>> results;

    private ListView plansListView;

    PlanFileAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_main_home, container, false);
        plansListView = fragmentView.findViewById(R.id.plans_listView);

        results = new ArrayList<>();
        loadPlans();

        adapter = new PlanFileAdapter(this, requireContext(), results);

        plansListView.setAdapter(adapter);

        return fragmentView;
    }
    public void move2PlanDisplayActivity(String fileName)
    {
        Intent intent = new Intent(requireContext(), PlanDisplayActivity.class);
        intent.putExtra("fileName", fileName); // 원본 파일명 전달
        startActivity(intent);
    }

    public void setListView()
    {
        results.clear();
        loadPlans();
        adapter = new PlanFileAdapter(this, requireContext(), results);
        plansListView.setAdapter(adapter);
    }

    private void loadPlans() {
        File directory = requireContext().getFilesDir();
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                if (!fileName.startsWith("default") && fileName.endsWith(".txt")) {
                    HashMap<String, String> map = new HashMap<>();
                    // "plan_" 제거 및 "_"를 공백으로 변환
                    String readableName = fileName.replace("plan_", "").replace("_", " ").replace(".txt", "");
                    map.put("primary", readableName); // 수정된 파일명 저장
                    map.put("fileName", fileName); // 원본 파일명 저장

                    // 파일 읽기
                    try (FileInputStream fis = new FileInputStream(file);
                         BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
                        StringBuilder fileContent = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            fileContent.append(line);
                        }

                        String[] splitContent = fileContent.toString().split("\\*");
                        if (splitContent.length >= 6) {
                            String destination = splitContent[1];
                            String startDate = splitContent[3];
                            String endDate = splitContent[5];
                            map.put("secondary", destination + " (" + startDate + " ~ " + endDate + ")");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        map.put("secondary", "Error reading file");
                    }

                    results.add(map);
                }
            }
        }
    }

}