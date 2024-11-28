package com.example.tpap.states;

import com.example.tpap.activities.PreplanActivity;
import com.example.tpap.fragments.TravelLocationFragment;
import com.example.tpap.fragments.TravelStyleFragment;

public class DateState implements BaseState{
    private PreplanActivity activity;

    public DateState(PreplanActivity activity)
    {
        this.activity = activity;
    }
    @Override
    public BaseState nextState() {
        if (activity.travel_startDate.isEmpty() || activity.travel_endDate.isEmpty())
        {
            activity.makeToast("You should select your travel date.");
            return null;
        }
        activity.fragmentTransaction(new TravelStyleFragment());
        return new StyleState(activity);
    }

    @Override
    public BaseState previousState() {
        activity.resetVM(1);
        activity.fragmentTransaction(new TravelLocationFragment());
        return new LocationState(activity);
    }

    @Override
    public void updateUI() {
        activity.setTitle("When");
        activity.setButtonText("Previous", "Next");
    }
}
