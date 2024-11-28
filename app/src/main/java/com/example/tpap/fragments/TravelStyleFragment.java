package com.example.tpap.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.tpap.R;
import com.example.tpap.view_models.TravelInfoViewModel;

import java.util.ArrayList;
import java.util.List;

public class TravelStyleFragment extends Fragment {
    private List<CheckBox> checkBoxes;
    private List<String> selectedStyles = new ArrayList<>();
    private static final int MAX_SELECTIONS = 3;

    private TravelInfoViewModel travelInfoVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_travel_style, container, false);

        travelInfoVM = new ViewModelProvider(requireActivity()).get(TravelInfoViewModel.class);

        checkBoxes = new ArrayList<>();
        checkBoxes.add(view.findViewById(R.id.option1CheckBox));
        checkBoxes.add(view.findViewById(R.id.option2CheckBox));
        checkBoxes.add(view.findViewById(R.id.option3CheckBox));
        checkBoxes.add(view.findViewById(R.id.option4CheckBox));
        checkBoxes.add(view.findViewById(R.id.option5CheckBox));
        checkBoxes.add(view.findViewById(R.id.option6CheckBox));
        checkBoxes.add(view.findViewById(R.id.option7CheckBox));
        checkBoxes.add(view.findViewById(R.id.option8CheckBox));
        checkBoxes.add(view.findViewById(R.id.option9CheckBox));

        for (CheckBox checkBox : checkBoxes) {
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                String style = buttonView.getText().toString();
                if (isChecked) {
                    if (selectedStyles.size() >= MAX_SELECTIONS) {
                        buttonView.setChecked(false);
                        Toast.makeText(requireContext(), "You can select up to " + MAX_SELECTIONS + " styles.", Toast.LENGTH_SHORT).show();
                    } else {
                        selectedStyles.add(style);
                        updateViewModel();
                    }
                } else {
                    selectedStyles.remove(style);
                    updateViewModel();
                }
            });
        }

        return view;
    }

    // ViewModel 업데이트 메서드
    private void updateViewModel() {
        StringBuilder formattedStyles = new StringBuilder();
        for (String style : selectedStyles) {
            formattedStyles.append("[").append(style).append("], ");
        }
        // 마지막 ", " 제거
        if (formattedStyles.length() > 0) {
            formattedStyles.setLength(formattedStyles.length() - 2);
        }
        travelInfoVM.style.setValue(formattedStyles.toString());
    }
}