package com.example.demo.model.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseUserDTO {
    private int id;
    private String username;
    private double balance;
}
