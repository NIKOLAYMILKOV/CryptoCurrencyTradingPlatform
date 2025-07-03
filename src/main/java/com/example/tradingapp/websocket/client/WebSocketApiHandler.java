package com.example.tradingapp.websocket.client;

import com.example.tradingapp.services.CryptoCurrencyDataService;
import com.example.tradingapp.websocket.server.MyWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

public class WebSocketApiHandler extends AbstractWebSocketHandler {

    @Autowired
    private CryptoCurrencyDataService cryptoCurrencyDataService;

    private final MyWebSocketHandler myWebSocketHandler;

    public WebSocketApiHandler(MyWebSocketHandler myWebSocketHandler) {
        this.myWebSocketHandler = myWebSocketHandler;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connected to external API");

        // Send subscription message if needed (depends on API)
        String subscribeMessage = "{\n" +
            "    \"method\": \"subscribe\",\n" +
            "    \"params\": {\n" +
            "        \"channel\": \"ticker\",\n" +
            "        \"symbol\": [\n" +
            "            \"ALGO/USD\"\n" +
            "        ]\n" +
            "    }\n" +
            "}";
        session.sendMessage(new TextMessage(subscribeMessage));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("Received from external API: " + message.getPayload());

        // Process message (example: forward raw or parse and modify)
        String processed = processMessage(message.getPayload());
//                System.out.println(processed);

        if (processed != null) {
            myWebSocketHandler.broadcast(processed);
        }
    }

    private String processMessage(String message) {
        if (message.contains("heartbeat")) {
            return null;
        }
        return message;
    }
}
