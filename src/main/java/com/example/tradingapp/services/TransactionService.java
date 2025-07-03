package com.example.tradingapp.services;

import com.example.tradingapp.exceptions.BadRequestException;
import com.example.tradingapp.model.Order;
import com.example.tradingapp.model.Transaction;
import com.example.tradingapp.model.TransactionMethod;
import com.example.tradingapp.model.dtos.ResponseUserDTO;
import com.example.tradingapp.repositories.DBTransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {
    @Autowired
    private DBTransactionRepository transactionRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserService userService;

    public Transaction buy(Order order) {
        ResponseUserDTO u = userService.getById(order.getUserId());
        if (u.getBalance() < order.getPrice() * order.getQuantity()) {
            throw new BadRequestException("Not enough balance");
        }
        userService.decreaseBalance(order);
        Transaction transaction = mapper.map(order, Transaction.class);
        transaction.setTransactionMethod(TransactionMethod.BUY);
        transaction.setTimestamp(LocalDateTime.now());

        return transactionRepository.add(transaction);
    }

    public Transaction sell(Order order) {
        ResponseUserDTO u = userService.getById(order.getUserId());
        if (u.getBalance() < order.getPrice() * order.getQuantity()) {
            throw new BadRequestException("Not enough balance");
        }
        userService.decreaseBalance(order);
        Transaction transaction = mapper.map(order, Transaction.class);
        transaction.setTransactionMethod(TransactionMethod.BUY);
        transaction.setTimestamp(LocalDateTime.now());

        return transactionRepository.add(transaction);
    }
}
