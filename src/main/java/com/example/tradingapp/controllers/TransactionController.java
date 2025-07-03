package com.example.tradingapp.controllers;

import com.example.tradingapp.model.Order;
import com.example.tradingapp.model.Symbol;
import com.example.tradingapp.model.Transaction;
import com.example.tradingapp.model.dtos.ResponseUserDTO;
import com.example.tradingapp.repositories.DBTransactionRepository;
import com.example.tradingapp.services.TransactionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController extends BaseController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/buy")
    public Transaction buy(@RequestBody Order order, HttpSession session) {
        validateLogged(session);
        int userId = (int) session.getAttribute(USER_ID);
        order.setUserId(userId);
        return transactionService.buy(order);
    }
}
