package com.example.tradingapp.websocket.client;

import com.example.tradingapp.websocket.server.MyWebSocketHandler;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Service
public class ExternalWebSocketClient {

    private static final String APU_URL = "wss://ws.kraken.com/v2";
    @Autowired
    private MyWebSocketHandler myWebSocketHandler;
    @Autowired
    private ExternalWebSocketApiHandler handler;

    @PostConstruct
    public void start() {
        StandardWebSocketClient client = new StandardWebSocketClient();
        client.execute(handler, APU_URL);
    }
}