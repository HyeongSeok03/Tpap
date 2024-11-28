package com.example.tpap.states;

public interface BaseState {
    BaseState nextState();
    BaseState previousState();
    void updateUI();
}
