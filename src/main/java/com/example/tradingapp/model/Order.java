package com.example.tradingapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Order {
    private int userId;
    private String symbol;
    private double price;
    private double quantity;
}
