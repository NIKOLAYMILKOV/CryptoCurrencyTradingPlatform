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

    private ConcurrentMap<String, CryptoCurrencyInfo> priceMap;

    public CryptoCurrencyDataService() {
        priceMap = new ConcurrentHashMap<>(INITIAL_CAPACITY, LOAD_FACTOR);
    }

    public void update(String symbol, CryptoCurrencyInfo cryptoCurrencyData) {
        priceMap.put(symbol, cryptoCurrencyData);
    }

    public ConcurrentMap<String, CryptoCurrencyInfo> update(String updateMessage) {
        ObjectMapper mapper = new ObjectMapper();
        UpdateMessageDTO updateMessageDTO;
        try {
            updateMessageDTO = mapper.readValue(updateMessage, UpdateMessageDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        for (CryptoCurrencyInfo currencyInfo : updateMessageDTO.getData()) {
            update(currencyInfo.getSymbol(), currencyInfo);
        }
        return priceMap;
    }

    public CryptoCurrencyInfo getCryptoCurrencyDataBySymbol(String symbol) {
        return priceMap.get(symbol);
    }
}