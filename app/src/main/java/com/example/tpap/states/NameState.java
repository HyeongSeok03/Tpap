package com.example.tpap.states;

import com.example.tpap.activities.PlanningActivity;
import com.example.tpap.fragments.LoadingFragment;
import com.example.tpap.fragments.TravelStyleFragment;

public class NameState implements BaseState{
    private PlanningActivity activity;
    public NameState(PlanningActivity activity)
    {
        this.activity = activity;
    }
    @Override
    public BaseState nextState() {
        if (activity.travel_fileName.isEmpty())
        {
            activity.makeToast("You should enter your file name");
            return null;
        }
        activity.fragmentTransaction(new LoadingFragment());
        return new LoadingState(activity);
    }

    @Override
    public BaseState previousState() {
        activity.resetVM(3);
        activity.fragmentTransaction(new TravelStyleFragment());
        return new StyleState(activity);
    }

    @Override
    public void updateUI() {
        activity.setTitle("Choose", "your travel name");
        activity.setButtonText("Previous", "Generate Plan");
    }
}
