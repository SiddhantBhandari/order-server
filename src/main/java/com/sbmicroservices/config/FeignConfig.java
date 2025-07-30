package com.sbmicroservices.config;


import com.sbmicroservices.apis.product_serverV2.decoder.CustomErrorDecoder;
import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    ErrorDecoder errorDecoder(){
        return new CustomErrorDecoder();
    }

    @Bean()
    Logger.Level feignConfigLevel(){
        return Logger.Level.FULL;
    }
}
