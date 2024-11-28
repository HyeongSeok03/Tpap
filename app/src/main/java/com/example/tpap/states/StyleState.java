package com.example.tpap.states;

import com.example.tpap.activities.PreplanActivity;
import com.example.tpap.fragments.TravelDateFragment;

import java.util.Date;

public class StyleState implements BaseState{
    private PreplanActivity activity;

    public StyleState(PreplanActivity activity)
    {
        this.activity = activity;
    }
    @Override
    public BaseState nextState() {
        if (activity.travel_style.isEmpty())
        {
            activity.makeToast("You should select your travel style");
        }
        return null;
    }

    @Override
    public BaseState previousState() {
        activity.resetVM(2);
        activity.fragmentTransaction(new TravelDateFragment());
        return new DateState(activity);
    }

    @Override
    public void updateUI() {
        activity.setTitle("How");
        activity.setButtonText("Previous", "Generate Plan");
    }
}
