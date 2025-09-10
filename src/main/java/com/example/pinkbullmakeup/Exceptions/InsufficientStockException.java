package com.example.pinkbullmakeup.Exceptions;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String s) {
        super(s);
    }
}
