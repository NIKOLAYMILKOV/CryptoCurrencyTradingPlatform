package com.example.tradingapp.websocket.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageSender {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public MessageSender(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Call this method when you want to send processed data to clients
    public void sendToClients(String processedMessage) {
        messagingTemplate.convertAndSend("/topic/price", processedMessage);
    }
}