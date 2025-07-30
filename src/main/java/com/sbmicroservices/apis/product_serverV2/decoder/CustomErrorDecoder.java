package com.sbmicroservices.apis.product_serverV2.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbmicroservices.exceptions.ApiCallException;
import com.sbmicroservices.exceptions.ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {


    @Override
    public Exception decode(String s, Response response){
        ObjectMapper objectMapper = new ObjectMapper();

        log.info("::{}", response.request().url());
        log.info("::{}", response.request().headers());

        try {
            ErrorResponse errorResponse = objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);
            return new ApiCallException(errorResponse.getMessage(), errorResponse.getTimestamp(), response.status());
        } catch (IOException e) {
            throw new ApiCallException("Internal Server Error", System.currentTimeMillis(), 500);
        }
    }
}
