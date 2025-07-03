package com.example.tradingapp.services;

import com.example.tradingapp.exceptions.BadRequestException;
import com.example.tradingapp.model.dtos.OrderDTO;
import com.example.tradingapp.model.Transaction;
import com.example.tradingapp.model.TransactionMethod;
import com.example.tradingapp.model.dtos.ResponseUserDTO;
import com.example.tradingapp.repositories.DBTransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private DBTransactionRepository transactionRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CryptoCurrencyDataService cryptoCurrencyDataService;

    public Transaction buy(OrderDTO order) {
        ResponseUserDTO u = userService.getById(order.getUserId());
        System.out.println(order.getSymbol());
        double price = cryptoCurrencyDataService.getCryptoCurrencyDataBySymbol(order.getSymbol()).getBid();
        if (u.getBalance() < price * order.getQuantity()) {
            throw new BadRequestException("Not enough balance");
        }

        double newBalance = u.getBalance() - price * order.getQuantity();
        userService.updateBalance(order.getUserId(), newBalance);

        Transaction transaction = mapper.map(order, Transaction.class);
        transaction.setTransactionMethod(TransactionMethod.BUY);
        transaction.setTimestamp(LocalDateTime.now());

        return transactionRepository.add(transaction);
    }

    public List<Transaction> transactions(int userId) {
        return transactionRepository.findAllByUserId(userId);
    }

//    public Transaction sell(OrderDTO order) {
////        ResponseUserDTO u = userService.getById(order.getUserId());
////        double price = cryptoCurrencyDataService.getCryptoCurrencyDataBySymbol(order.getSymbol()).getAsk();
//////        if (u.getBalance() < order.getPrice() * order.getQuantity()) {
//////            throw new BadRequestException("Not enough balance");
//////        }
////        userService.decreaseBalance(order);
////        Transaction transaction = mapper.map(order, Transaction.class);
////        transaction.setTransactionMethod(TransactionMethod.BUY);
////        transaction.setTimestamp(LocalDateTime.now());
////
////        return transactionRepository.add(transaction);
//    }
}
