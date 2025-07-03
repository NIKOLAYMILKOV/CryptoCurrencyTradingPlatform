package com.example.tradingapp.websocket.client;

import com.example.tradingapp.services.CryptoCurrencyDataService;
import com.example.tradingapp.websocket.server.MyWebSocketHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

public class WebSocketApiHandler extends AbstractWebSocketHandler {

    private final CryptoCurrencyDataService cryptoCurrencyDataService;
    private final MyWebSocketHandler myWebSocketHandler;

    public WebSocketApiHandler(MyWebSocketHandler myWebSocketHandler) {
        this.myWebSocketHandler = myWebSocketHandler;
        this.cryptoCurrencyDataService = new CryptoCurrencyDataService();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connected to external API");

        String subscribeMessage = "{\n" +
            "    \"method\": \"subscribe\",\n" +
            "    \"params\": {\n" +
            "        \"channel\": \"ticker\",\n" +
            "        \"symbol\": [\n" +
            "            \"BTC/USD\",\n" +
            "            \"ETH/USD\",\n" +
            "            \"USDT/USD\",\n" +
            "            \"BNB/USD\",\n" +
            "            \"SOL/USD\",\n" +
            "            \"XRP/USD\",\n" +
            "            \"USDC/USD\",\n" +
            "            \"DOGE/USD\",\n" +
            "            \"TON/USD\",\n" +
            "            \"ADA/USD\",\n" +
            "            \"AVAX/USD\",\n" +
            "            \"SHIB/USD\",\n" +
            "            \"DOT/USD\",\n" +
            "            \"WTRX/USD\",\n" +
            "            \"LINK/USD\",\n" +
            "            \"ICP/USD\",\n" +
            "            \"MATIC/USD\",\n" +
            "            \"TRX/USD\",\n" +
            "            \"BCH/USD\",\n" +
            "            \"LTC/USD\"\n" +
            "        ]\n" +
            "    }\n" +
            "}\n";
        session.sendMessage(new TextMessage(subscribeMessage));
    }

    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) {
        System.out.println("Received from external API: " + message.getPayload());

        String processed = processMessage(message.getPayload());
        if (processed != null) {
            String msg;
            try {
                msg = new ObjectMapper().writeValueAsString(cryptoCurrencyDataService.update(processed));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            myWebSocketHandler.broadcast(msg);
        }
    }

    private String processMessage(String message) {
        if (message.contains("ticker") && (message.contains("update") || message.contains("snapshot"))) {
            return message;
        }
        return null;
    }
}
