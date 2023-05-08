package com.example.tamozhpenies.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler extends RuntimeException {
    String msg;
    public ExceptionHandler() {

    }
}
