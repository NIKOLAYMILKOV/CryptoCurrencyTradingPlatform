package com.example.tradingapp.repositories;

import com.example.tradingapp.model.Transaction;

import java.util.List;

public interface TransactionRepository {
    List<Transaction> findAllByUserId(int userId);

    Transaction add(Transaction transaction);

    void deleteByUserId(int userId);
}
