package com.example.tpap.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.tpap.R;
import com.example.tpap.view_models.TravelInfo;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;

public class TravelLocationFragment extends Fragment {
    private ArrayAdapter<String> adapter;
    private ArrayList<String> results;
    private PlacesClient placesClient;

    private TravelInfo travelInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View fragmentView = inflater.inflate(R.layout.fragment_travel_location, container, false);

        SearchView searchView = fragmentView.findViewById(R.id.searchView);
        ListView results_listView = fragmentView.findViewById(R.id.results_listView);

        travelInfo = new ViewModelProvider(requireActivity()).get(TravelInfo.class);

        results = new ArrayList<>();
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, results);
        results_listView.setAdapter(adapter);

        Places.initialize(requireContext(), "AIzaSyBVMQQda-Awz_hWgiRiPBozdNFt5Bf2KIc");
        placesClient = Places.createClient(requireContext());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                findPlaces(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    results_listView.setVisibility(View.GONE);
                } else {
                    results_listView.setVisibility(View.VISIBLE);
                    // 필터링 또는 검색 수행
                    findPlaces(newText);
                }
                return false;
            }
        });

        results_listView.setOnItemClickListener((parent, view, position, id) -> {
            String location = adapter.getItem(position);
            searchView.setQuery(location, false);  // false로 설정하여 직접 검색을 트리거하지 않음
            // 키보드를 숨기기 위해 InputMethodManager를 사용
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
            }
            // 리스트뷰를 숨기기
            results_listView.setVisibility(View.GONE);
            // 여기서 location 값을 처리하면 됩니다.
            travelInfo.location.setValue(location);
        });

        return fragmentView;

    }

    private void findPlaces(String query) {
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setQuery(query)
                .build();

        placesClient.findAutocompletePredictions(request).addOnSuccessListener(response -> {
            results.clear();
            for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                results.add(prediction.getPrimaryText(null).toString());
            }
            adapter.notifyDataSetChanged();
        }).addOnFailureListener(exception -> {
            // Handle the error.
        });
    }
}