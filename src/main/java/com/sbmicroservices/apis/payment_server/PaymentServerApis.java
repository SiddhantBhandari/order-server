package com.sbmicroservices.apis.payment_server;


import com.sbmicroservices.apis.payment_server.request.PaymentRequest;
import com.sbmicroservices.config.FeignConfig;
import com.sbmicroservices.models.response.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-server", url = "http://localhost:4003", path = "/payments", configuration = FeignConfig.class)
public interface PaymentServerApis {


    @PostMapping("/pay")
    ResponseEntity<SuccessResponse> doPayment(@RequestBody  PaymentRequest request);
}
