package com.example.tpap.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.tpap.R;
import com.example.tpap.view_models.TravelInfoViewModel;

import java.util.Calendar;

public class TravelDateFragment extends Fragment {

    private EditText arrivalDate_editText, departureDate_editText;

    private Calendar calendar = Calendar.getInstance();

    private long startDateMillis = -1;
    private long endDateMillis = -1;    // 종료 날짜를 밀리초로 저장

    private TravelInfoViewModel travelInfoVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_travel_date, container, false);

        travelInfoVM = new ViewModelProvider(requireActivity()).get(TravelInfoViewModel.class);

        arrivalDate_editText = fragmentView.findViewById(R.id.arrivalDate_editText);
        departureDate_editText = fragmentView.findViewById(R.id.departureDate_editText);

        arrivalDate_editText.setOnClickListener(v -> showStartDatePickerDialog());
        departureDate_editText.setOnClickListener(v -> showEndDatePickerDialog());

        return fragmentView;
    }

    private void showStartDatePickerDialog() {
        DatePickerDialog startDatePickerDialog = new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            startDateMillis = calendar.getTimeInMillis();
            String startDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);

            // 시작 날짜가 종료 날짜보다 늦으면 종료 날짜 초기화
            if (endDateMillis != -1 && startDateMillis > endDateMillis) {
                departureDate_editText.setText(""); // 종료 날짜 초기화
                endDateMillis = -1; // 종료 날짜의 밀리초 값 초기화
                travelInfoVM.endDate.setValue(""); // ViewModel에서도 초기화
            }

            arrivalDate_editText.setText(startDate);
            travelInfoVM.startDate.setValue(startDate);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        startDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000); // 오늘 날짜 이전 선택 불가
        startDatePickerDialog.show();
    }

    private void showEndDatePickerDialog() {
        if (startDateMillis == -1) {  // 시작 날짜가 설정되지 않은 경우
            departureDate_editText.setError("먼저 시작 날짜를 설정해주세요.");
            return;
        }

        DatePickerDialog endDatePickerDialog = new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) ->
        {
            calendar.set(year, month, dayOfMonth);
            endDateMillis = calendar.getTimeInMillis();
            String endDate =String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);

            departureDate_editText.setText(endDate);
            travelInfoVM.endDate.setValue(endDate);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        endDatePickerDialog.getDatePicker().setMinDate(startDateMillis); // 시작 날짜 이후만 선택 가능
        endDatePickerDialog.getDatePicker().setMaxDate(startDateMillis + (1000L * 60 * 60 * 24 * 6)); // 14일 (밀리초)
        endDatePickerDialog.show();
    }
}