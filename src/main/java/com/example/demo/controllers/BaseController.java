package com.example.demo.controllers;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.UnauthorisedException;
import com.example.demo.model.dtos.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseController {
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
}