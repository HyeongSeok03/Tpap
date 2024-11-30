package com.example.tpap.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import com.example.tpap.R;
import com.example.tpap.view_models.TravelInfoViewModel;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TravelLocationFragment extends Fragment {
    private SimpleAdapter adapter;
    private List<HashMap<String, String>> results;
    private PlacesClient placesClient;

    private TravelInfoViewModel travelInfoVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View fragmentView = inflater.inflate(R.layout.fragment_travel_location, container, false);

        SearchView searchView = fragmentView.findViewById(R.id.searchView);

        ListView results_listView = fragmentView.findViewById(R.id.results_listView);

        travelInfoVM = new ViewModelProvider(requireActivity()).get(TravelInfoViewModel.class);

        results = new ArrayList<>();
        adapter = new SimpleAdapter(requireContext(), results, android.R.layout.simple_list_item_2, new String[]{"primary", "secondary"}, new int[] {android.R.id.text1, android.R.id.text2});
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
                    travelInfoVM.destination.setValue(newText);
                } else {
                    results_listView.setVisibility(View.VISIBLE);
                    // 필터링 또는 검색 수행
                    findPlaces(newText);
                }
                return false;
            }
        });

        results_listView.setOnItemClickListener((parent, view, position, id) -> {
            HashMap<String, String> selectedItem = (HashMap<String, String>)adapter.getItem(position);
            String primary = selectedItem.get("primary");
            String secondary = selectedItem.get("secondary");
            String location = primary;
            if (!secondary.isEmpty()) {
                location += String.format(" (%s)", secondary);
            }

            searchView.setQuery(location, false);  // false로 설정하여 직접 검색을 트리거하지 않음
            // 키보드를 숨기기 위해 InputMethodManager를 사용
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
            }
            // 리스트뷰를 숨기기
            results_listView.setVisibility(View.GONE);

            travelInfoVM.destination.setValue(location);
        });

        return fragmentView;
    }

    private void findPlaces(String query) {
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setQuery(query).setTypesFilter(Arrays.asList("country", "locality"))
                .build();

        placesClient.findAutocompletePredictions(request).addOnSuccessListener(response -> {
            results.clear();
            for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                String primaryText = prediction.getPrimaryText(null).toString();
                String secondaryText = prediction.getSecondaryText(null).toString();
                HashMap<String, String> item = new HashMap<>();
                item.put("primary", primaryText); // 도시 이름
                item.put("secondary", secondaryText); // 국가 이름
                results.add(item);
            }
            adapter.notifyDataSetChanged();
        }).addOnFailureListener(exception -> {
            // Handle the error.
        });
    }
}