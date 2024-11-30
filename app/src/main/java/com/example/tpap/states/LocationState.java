package com.example.tpap.states;

import com.example.tpap.activities.MainActivity;
import com.example.tpap.activities.PlanningActivity;
import com.example.tpap.fragments.TravelDateFragment;

public class LocationState implements BaseState{
    private PlanningActivity activity;

    public LocationState(PlanningActivity activity)
    {
        this.activity = activity;
    }
    @Override
    public BaseState nextState() {
        if (activity.travel_destination.isEmpty())
        {
            activity.makeToast("You should select your destination.");
            return null;
        }
        activity.fragmentTransaction(new TravelDateFragment());
        return new DateState(activity);
    }

    @Override
    public BaseState previousState() {
        activity.activityTransaction(MainActivity.class);
        return null;
    }

    @Override
    public void updateUI() {
        activity.setTitle("Where");
        activity.setButtonText("Cancel", "Next");
    }
}
