package com.example.pinkbullmakeup.Exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String item) {
        super(item + " already exists.");
    }
}
