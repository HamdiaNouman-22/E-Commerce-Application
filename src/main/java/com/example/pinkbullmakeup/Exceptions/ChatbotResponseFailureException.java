package com.example.pinkbullmakeup.Exceptions;

public class ChatbotResponseFailureException extends RuntimeException{
    public ChatbotResponseFailureException(String s){
        super(s);
    }
}
