package com.example.tradingapp.exceptions;

public class UnauthorisedException extends RuntimeException {
    public UnauthorisedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorisedException(String message) {
        super(message);
    }
}
