package com.example.tradingapp.websocket.client;

import com.example.tradingapp.websocket.server.MessageSender;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Service
public class ExternalWebSocketClient {

    private static final String API_URL = "wss://ws.kraken.com/v2";

    @Autowired
    private MessageSender messageSender;

    @PostConstruct
    public void start() {
        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketHandler handler = new CustomWebSocketHandler(messageSender);
        client.execute(handler, API_URL);
    }
}