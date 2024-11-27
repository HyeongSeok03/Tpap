package com.example.tpap.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TravelInfoViewModel extends ViewModel {
    public MutableLiveData<String> destination = new MutableLiveData<>();
    public MutableLiveData<String> startDate = new MutableLiveData<>();
    public MutableLiveData<String> endDate = new MutableLiveData<>();
    public MutableLiveData<String> style = new MutableLiveData<>();
}
