package com.example.tpap.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.tpap.R;
import com.example.tpap.view_models.TravelInfoViewModel;

public class TravelNameFragment extends Fragment {

    EditText fileName_editText;

    private TravelInfoViewModel travelInfoVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_travel_name, container, false);

        fileName_editText = fragmentView.findViewById(R.id.fileName_editText);
        travelInfoVM = new ViewModelProvider(requireActivity()).get(TravelInfoViewModel.class);

        fileName_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                travelInfoVM.fileName.setValue(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return fragmentView;
    }
}