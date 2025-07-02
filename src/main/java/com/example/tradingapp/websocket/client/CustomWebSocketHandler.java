package com.example.tradingapp.websocket.client;

import com.example.tradingapp.websocket.server.MessageSender;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

public class CustomWebSocketHandler extends AbstractWebSocketHandler {

    private final MessageSender messageSender;

    public CustomWebSocketHandler(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connected to external WebSocket API");
        // Subscribe to the stream (API-specific)
        session.sendMessage(new TextMessage("{\n" +
            "    \"method\": \"subscribe\",\n" +
            "    \"params\": {\n" +
            "        \"channel\": \"ticker\",\n" +
            "        \"symbol\": [\n" +
            "            \"ALGO/USD\"\n" +
            "        ]\n" +
            "    }\n" +
            "}"));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("Message from external API: " + message.getPayload());

        // Process the message (parse JSON, extract fields, etc.)
        String processed = processMessage(message.getPayload());

        // Send processed message to frontend clients via STOMP topic
        messageSender.sendToClients(processed);
    }

    private String processMessage(String rawMessage) {
        // Here you can parse JSON, filter or transform data
        return rawMessage;
    }
}