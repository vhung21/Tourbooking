package com.hungnv.tourbooking.service;

import com.hungnv.tourbooking.dto.ChatBotDTO;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RasaClient {

    private final WebClient webClient;

    public RasaClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:5005").build();
    }

    public List<ChatBotDTO.ChatReply> sendMessage(String sender, String message) {
        Map<String, String> body = Map.of("sender", sender, "message", message);

        List<Map<String, Object>> rasaMsgs = webClient
            .post()
            .uri("/webhooks/rest/webhook")
            .bodyValue(body)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
            .block();

        if (rasaMsgs == null) return List.of();

        return rasaMsgs.stream().map(m -> (String) m.get("text")).filter(Objects::nonNull).map(ChatBotDTO.ChatReply::new).toList();
    }
}
