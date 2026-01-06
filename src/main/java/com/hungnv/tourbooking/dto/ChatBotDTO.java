package com.hungnv.tourbooking.dto;

public class ChatBotDTO {

    public record ChatRequest(String sender, String message) {}

    public record ChatReply(String text) {}

    public record ChatResponse(java.util.List<ChatReply> replies) {}
}
