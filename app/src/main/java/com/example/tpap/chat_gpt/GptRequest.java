package com.example.tpap.chat_gpt;

import java.util.List;

public class GptRequest {
    private String model;
    private List<Message> messages;
    private double temperature;

    public GptRequest(List<Message> messages) {
        this.model = "gpt-4o";
        this.messages = messages;
        this.temperature = 0.7;
    }
}
