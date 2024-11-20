package com.example.tpap.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TravelInfo extends ViewModel {
    public MutableLiveData<String> location = new MutableLiveData<>();
    public MutableLiveData<String> startDate = new MutableLiveData<>();
    public MutableLiveData<String> endDate = new MutableLiveData<>();
    public MutableLiveData<String> style = new MutableLiveData<>();
}
