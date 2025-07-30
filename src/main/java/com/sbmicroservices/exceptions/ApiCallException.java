package com.sbmicroservices.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class ApiCallException extends RuntimeException{

    private String message;

    private Long timestamp;

    private int status;


    public ApiCallException(String message, Long timestamp, int status) {
        super(message);
        this.message = message;
        this.timestamp = timestamp;
        this.status = status;
    }
}
