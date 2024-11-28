package com.example.tpap.states;

import android.widget.Toast;

import com.example.tpap.R;
import com.example.tpap.activities.MainActivity;
import com.example.tpap.activities.PreplanActivity;
import com.example.tpap.fragments.TravelDateFragment;

public class LocationState implements BaseState{
    private PreplanActivity activity;

    public LocationState(PreplanActivity activity)
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
