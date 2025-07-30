package com.sbmicroservices.models.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class OrderResponse {

    private String orderId;

    private Long id;

    private String message;

    private boolean success = true;

    public OrderResponse(String orderId, Long id, String message, boolean success) {
        this.orderId = orderId;
        this.id = id;
        this.message = message;
        this.success = success;
    }
}
