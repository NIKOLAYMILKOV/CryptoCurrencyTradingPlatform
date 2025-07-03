package com.example.tradingapp.controllers;

import com.example.tradingapp.exceptions.BadRequestException;
import com.example.tradingapp.exceptions.UnauthorisedException;
import com.example.tradingapp.model.dtos.ErrorDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseController {

    public static final String LOGGED = "logged";
    public static final String USER_ID = "user_id";

    @ExceptionHandler(exception = UnauthorisedException.class)
    public ResponseEntity<ErrorDto> unauthorisedHandler(Exception e) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(e.getMessage());
        errorDto.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(errorDto);
    }

    @ExceptionHandler(exception = BadRequestException.class)
    public ResponseEntity<ErrorDto> badRequestHandler(Exception e) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(e.getMessage());
        errorDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorDto);
    }

    protected void validateLogged(HttpSession session) {
        if (session.getAttribute(LOGGED) == null || !(boolean)session.getAttribute(LOGGED)) {
            throw new UnauthorisedException("Log in first");
        }
    }
}