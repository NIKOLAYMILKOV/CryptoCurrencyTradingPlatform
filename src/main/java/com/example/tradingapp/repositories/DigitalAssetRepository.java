package com.example.tradingapp.repositories;

import com.example.tradingapp.model.DigitalAsset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DigitalAssetRepository {
    @Autowired
    private Connection connection;

    public List<DigitalAsset> findAllByUserId(int userId) {
        PreparedStatement statement;
        List<DigitalAsset> digitalAssets = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM digital_assets WHERE user_id=?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                digitalAssets.add(new DigitalAsset(resultSet.getInt("id"),
                    resultSet.getString("symbol"),
                    resultSet.getDouble("quantity"),
                    resultSet.getInt("user_id")));
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not retrieve assets", e);
        }
        return digitalAssets;
    }

    public DigitalAsset findBySymbolAndUserId(String symbol, int userId) {
        PreparedStatement statement;
        DigitalAsset asset;
        try {
            statement = connection.prepareStatement("SELECT * FROM digital_assets WHERE symbol=? AND user_id=?");
            statement.setString(1, symbol);
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            asset = new DigitalAsset(resultSet.getInt("id"),
                resultSet.getString("symbol"),
                resultSet.getDouble("quantity"),
                resultSet.getInt("user_id"));

        } catch (Exception e) {
            throw new RuntimeException("Could not retrieve assets - " + e.getMessage(), e);
        }
        return asset;
    }

    public DigitalAsset save(DigitalAsset asset) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("INSERT INTO digital_assets (symbol, quantity, user_id)" +
                "VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, asset.getSymbol());
            statement.setDouble(2, asset.getQuantity());
            statement.setInt(3, asset.getUserId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Could not save asset");
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            asset.setId(resultSet.getInt("id"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return asset;
    }

    public DigitalAsset edit(DigitalAsset asset) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("UPDATE digital_assets SET quantity=? " +
                "WHERE user_id=? AND symbol=?");
            statement.setString(1, asset.getSymbol());
            statement.setInt(2, asset.getUserId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Could not edit asset");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return asset;
    }
}
