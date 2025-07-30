package com.sbmicroservices.models.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessResponse {

    private int code;

    private String message;

    private boolean success = true;
}
