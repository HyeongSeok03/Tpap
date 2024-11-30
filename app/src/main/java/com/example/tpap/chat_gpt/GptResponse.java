package com.example.tpap.chat_gpt;

import java.util.List;

public class GptResponse {
    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public static class Choice {
        private Message message;

        public Message getMessage() {
            return message;
        }
    }
}
