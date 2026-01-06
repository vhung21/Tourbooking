package com.hungnv.tourbooking.web.rest;

import com.hungnv.tourbooking.dto.ChatBotDTO;
import com.hungnv.tourbooking.service.RasaClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final RasaClient rasaClient;

    public ChatController(RasaClient rasaClient) {
        this.rasaClient = rasaClient;
    }

    @PostMapping
    public ChatBotDTO.ChatResponse chat(@RequestBody ChatBotDTO.ChatRequest req) {
        var replies = rasaClient.sendMessage(req.sender(), req.message());
        return new ChatBotDTO.ChatResponse(replies);
    }
}
