package com.example.tradingapp.controllers;

import com.example.tradingapp.model.Transaction;
import com.example.tradingapp.model.dtos.OrderDTO;
import com.example.tradingapp.model.dtos.ResponseUserDTO;
import com.example.tradingapp.services.TransactionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController extends BaseController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/buy")
    public ResponseUserDTO buy(@RequestBody OrderDTO order, HttpSession session) {
        validateLogged(session);
        int userId = (int) session.getAttribute(USER_ID);
        order.setUserId(userId);
        return transactionService.buy(order);
    }

    @PostMapping("/sell")
    public ResponseUserDTO sell(@RequestBody OrderDTO order, HttpSession session) {
        validateLogged(session);
        int userId = (int) session.getAttribute(USER_ID);
        order.setUserId(userId);
        return transactionService.sell(order);
    }

    @GetMapping("transactions/history")
    public List<Transaction> transactionHistory(HttpSession session) {
        validateLogged(session);
        int userId = (int) session.getAttribute(USER_ID);
        return transactionService.transactions(userId);
    }
}
