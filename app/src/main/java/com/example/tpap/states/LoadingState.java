package com.example.tpap.states;

import com.example.tpap.activities.PlanningActivity;

public class LoadingState implements BaseState{
    private PlanningActivity activity;

    public LoadingState(PlanningActivity activity)
    {
        this.activity = activity;
    }
    @Override
    public BaseState nextState() {
        return null;
    }

    @Override
    public BaseState previousState() {
        return null;
    }

    @Override
    public void updateUI() {
        activity.setFragmentFull();
    }
}
