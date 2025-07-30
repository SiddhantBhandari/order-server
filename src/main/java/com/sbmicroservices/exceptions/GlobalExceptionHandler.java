package com.sbmicroservices.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage(), System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    private ResponseEntity<ErrorResponse> handleUserAlreadyExistsExceptionException(UserAlreadyExistsException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage(), System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAttributesException.class)
    private ResponseEntity<ErrorResponse> handleInvalidAttributesException(InvalidAttributesException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage(), System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiCallException.class)
    private ResponseEntity<ErrorResponse> handleApiCallException(ApiCallException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleOrderNotFoundException(OrderNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), System.currentTimeMillis(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
