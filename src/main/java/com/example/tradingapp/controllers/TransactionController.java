package com.example.tradingapp.controllers;

import com.example.tradingapp.model.dtos.ResponseUserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TransactionController extends BaseController {
    @PostMapping("/order")
    public ResponseUserDTO buy() {
        return null;
    }
}
