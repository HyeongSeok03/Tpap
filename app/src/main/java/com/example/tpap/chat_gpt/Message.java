package com.example.tpap.chat_gpt;

public class Message {
    public static final String ROLE_ASSISTANT = "assistant"; // 챗봇 메시지
    public static final String ROLE_USER = "user"; // 내 메시지

    public String role; // 누가 보낸 메시지인지 확인
    public String content; // 메시지 내용

    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }
}