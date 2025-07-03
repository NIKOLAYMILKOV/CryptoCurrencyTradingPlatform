package com.example.tradingapp.websocket.client;

import com.example.tradingapp.websocket.server.MyWebSocketHandler;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Service
public class ExternalWebSocketClient {

    private final MyWebSocketHandler myWebSocketHandler;

    public ExternalWebSocketClient(MyWebSocketHandler myWebSocketHandler) {
        this.myWebSocketHandler = myWebSocketHandler;
    }

    @PostConstruct
    public void start() {
        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketHandler handler = new WebSocketApiHandler(myWebSocketHandler);
        client.execute(handler, "wss://ws.kraken.com/v2");
    }
}