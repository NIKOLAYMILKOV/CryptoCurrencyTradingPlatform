package com.example.tradingapp.repositories;

import com.example.tradingapp.model.DigitalAsset;

import java.util.List;

public interface DigitalAssetRepository {
    List<DigitalAsset> findAllByUserId(int userId);

    DigitalAsset findBySymbolAndUserId(String symbol, int userId);

    DigitalAsset save(DigitalAsset asset);

    DigitalAsset edit(DigitalAsset asset);

    void deleteByUserId(int userId);
}
