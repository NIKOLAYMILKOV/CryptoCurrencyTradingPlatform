package com.example.tradingapp.repositories;

import com.example.tradingapp.model.Transaction;
import com.example.tradingapp.model.TransactionMethod;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBTransactionRepository {

    @Autowired
    private Connection connection;

    public synchronized List<Transaction> findAllByUserId(int userId) {
        PreparedStatement statement;
        List<Transaction> transactions = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM transactions WHERE user_id=?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction(resultSet.getInt("id"),
                                                            resultSet.getString("symbol"),
                                                            resultSet.getDouble("quantity"),
                                                            resultSet.getDouble("price"),
                    TransactionMethod.valueOf(resultSet.getString("transaction_method")),
                    resultSet.getTimestamp("timestamp").toLocalDateTime(),
                    resultSet.getInt("user_id"));
                transactions.add(transaction);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    public Transaction add(Transaction transaction) {
        PreparedStatement statement;

        try {
            statement = connection.prepareStatement("INSERT INTO transactions " +
                "(symbol, quantity, price, transaction_method, timestamp, user_id)" +
                "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, transaction.getSymbol());
            statement.setDouble(2, transaction.getQuantity());
            statement.setDouble(3, transaction.getPrice());
            statement.setString(4, transaction.getTransactionMethod().toString());
            statement.setDate(5, Date.valueOf(transaction.getTimestamp().toLocalDate()));
            statement.setInt(6, transaction.getUserId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
