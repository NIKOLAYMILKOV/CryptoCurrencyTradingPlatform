package com.example.tradingapp.services;

import com.example.tradingapp.model.CryptoCurrencyInfo;
import com.example.tradingapp.model.Symbol;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class CryptoCurrencyDataService {
    public static final int INITIAL_CAPACITY = 1000;
    public static final float LOAD_FACTOR = 0.75f;
    private final ConcurrentMap<Symbol, CryptoCurrencyInfo> priceMap;

    public CryptoCurrencyDataService() {
        priceMap = new ConcurrentHashMap<>(INITIAL_CAPACITY, LOAD_FACTOR);
    }

    public ConcurrentMap<Symbol, CryptoCurrencyInfo> update(Symbol symbol, CryptoCurrencyInfo cryptoCurrencyData) {
        priceMap.put(symbol, cryptoCurrencyData);
        return priceMap;
    }

    public ConcurrentMap<Symbol, CryptoCurrencyInfo> update(String updateMessage) {
        //TODO parce the message into Symbol obj and CryptoCurrencyInfo obj
        return update(null, null);
    }

    public CryptoCurrencyInfo getCryptoCurrencyDataBySymbol(Symbol symbol) {
        return priceMap.getOrDefault(symbol, null);
    }
}