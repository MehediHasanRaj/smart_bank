package com.raj.smart_bank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Chatbot Management")
@RestController
@RequestMapping("/ai")
@Slf4j
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Operation(summary = "Chatbot for customer's query")
    @PostMapping("/chatbot")
    public String chat(@RequestHeader String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

}
