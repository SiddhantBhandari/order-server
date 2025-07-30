package com.sbmicroservices.exceptions;

public class InvalidAttributesException extends Exception{

    private String message;

    public InvalidAttributesException(String message){
        super(message);
    }
}
