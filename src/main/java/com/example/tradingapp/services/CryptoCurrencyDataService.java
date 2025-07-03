package com.example.tradingapp.services;

import com.example.tradingapp.model.CryptoCurrencyInfo;
import com.example.tradingapp.model.UpdateMessageDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class CryptoCurrencyDataService {
    public static final int INITIAL_CAPACITY = 1000;
    public static final float LOAD_FACTOR = 0.75f;
    private final ConcurrentMap<String, CryptoCurrencyInfo> priceMap;

    public CryptoCurrencyDataService() {
        priceMap = new ConcurrentHashMap<>(INITIAL_CAPACITY, LOAD_FACTOR);
//        System.out.println("JJHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
    }

    public ConcurrentMap<String, CryptoCurrencyInfo> update(String symbol, CryptoCurrencyInfo cryptoCurrencyData) {
        if(priceMap == null) {
            throw new IllegalArgumentException("map null");
        }
        priceMap.put(symbol, cryptoCurrencyData);
        return priceMap;
    }

    public ConcurrentMap<String, CryptoCurrencyInfo> update(String updateMessage) {
        ObjectMapper mapper = new ObjectMapper();
        UpdateMessageDTO updateMessageDTO;
        try {
            updateMessageDTO = mapper.readValue(updateMessage, UpdateMessageDTO.class);
            System.out.println("UPDATE MESSAGE" + updateMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(updateMessageDTO.getData());
        updateMessageDTO.getData()
            .forEach(cryptoCurrencyInfo -> update(cryptoCurrencyInfo.getSymbol(), cryptoCurrencyInfo));

        return priceMap;
    }

    public CryptoCurrencyInfo getCryptoCurrencyDataBySymbol(String symbol) {
        return priceMap.getOrDefault(symbol, null);
    }
}