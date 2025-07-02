package com.example.tradingapp.websocket.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketServerConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");        // Enables in-memory broker for topics
        config.setApplicationDestinationPrefixes("/app"); // Prefix for messages sent from clients to server (not needed for you now)
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp")  // The URL your clients connect to
            .setAllowedOriginPatterns("*")   // Allow any origin (dev only)
            .withSockJS();
        System.out.println("hello"); // Enables fallback for browsers without WS support
    }
}