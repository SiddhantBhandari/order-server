package com.sbmicroservices.exceptions;



public class OrderNotFoundException extends Exception{

    private String message;

    public OrderNotFoundException(String message) {
        super(message);
    }
}
