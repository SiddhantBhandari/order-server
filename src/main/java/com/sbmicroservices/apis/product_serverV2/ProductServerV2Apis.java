package com.sbmicroservices.apis.product_serverV2;

import com.sbmicroservices.apis.product_server.response.GetProductResponse;
import com.sbmicroservices.config.FeignConfig;
import com.sbmicroservices.exceptions.ApiCallException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@FeignClient(name = "product-server/product", configuration = FeignConfig.class)
public interface ProductServerV2Apis {


    @PutMapping("/reduceQuantity/{id}/quantity/{qtty}")
    ResponseEntity<Void> reduceQuantity(@PathVariable(value = "id", name = "id") String productId, @PathVariable("qtty") Double qtty);


    @GetMapping("/{id}/{code}")
    @CircuitBreaker(name = "external", fallbackMethod = "fallback")
    ResponseEntity<GetProductResponse> getProductDetails(@PathVariable("id") String id, @PathVariable("code") String code);

    default ResponseEntity<GetProductResponse> fallback(String id, String code, Exception e){
        throw new ApiCallException("Product server not available", System.currentTimeMillis(), 500);
    }
}
