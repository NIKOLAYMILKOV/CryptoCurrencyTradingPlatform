package com.example.tradingapp.controllers;

import com.example.tradingapp.model.Symbol;
import com.example.tradingapp.model.dtos.ResponseUserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController extends BaseController {
    @PostMapping("/buy")
    public ResponseUserDTO buy(@RequestBody Symbol) {

    }
}
