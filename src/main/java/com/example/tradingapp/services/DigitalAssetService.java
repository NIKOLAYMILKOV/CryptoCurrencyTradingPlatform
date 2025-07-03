package com.example.tradingapp.services;

import com.example.tradingapp.exceptions.BadRequestException;
import com.example.tradingapp.model.DigitalAsset;
import com.example.tradingapp.repositories.DigitalAssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DigitalAssetService {

    @Autowired
    private DigitalAssetRepository digitalAssetRepository;

    List<DigitalAsset> getAllByUserId(int userId) {
        return digitalAssetRepository.findAllByUserId(userId);
    }

    public DigitalAsset findBySymbolAndUserId(String symbol, int userId) {
        if (symbol == null || symbol.isBlank()) {
            throw new BadRequestException("Symbol is mandatory");
        }
        DigitalAsset asset = digitalAssetRepository.findBySymbolAndUserId(symbol, userId);
//        if (asset == null) {
//            throw new BadRequestException("You do not own asset with symbol");
//        }
        return asset;
    }

    public DigitalAsset buy(DigitalAsset boughtAsset) {
        DigitalAsset digitalAsset = findBySymbolAndUserId(boughtAsset.getSymbol(), boughtAsset.getUserId());
        if (digitalAsset == null) {
            return digitalAssetRepository.save(boughtAsset);
        }
        digitalAsset.setQuantity(digitalAsset.getQuantity() + boughtAsset.getQuantity());
        return digitalAssetRepository.edit(digitalAsset);
    }

    public DigitalAsset sell(DigitalAsset soldAsset) {
        DigitalAsset digitalAsset = findBySymbolAndUserId(soldAsset.getSymbol(), soldAsset.getUserId());
        if (Double.compare(digitalAsset.getQuantity(), soldAsset.getQuantity()) <= 0) {
            throw new BadRequestException("Owned quantity is less than the selling quantity");
        }
        digitalAsset.setQuantity(digitalAsset.getQuantity() - soldAsset.getQuantity());
        return digitalAssetRepository.edit(digitalAsset);
    }
}
