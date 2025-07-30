package com.sbmicroservices.apis.payment_server.request;


import com.sbmicroservices.libs.orders.Orders;
import lombok.Data;

@Data
public class PaymentRequest {

    private Long userId;

    private Long orderId;

    private Double amount;

    private String paymentMethod;

    private String currency;

    public PaymentRequest(Orders orders) {
        this.userId = orders.getUser().getId();
        this.orderId = orders.getId();
        this.amount = orders.getTotalAmount();
        this.currency = orders.getCurrency();
    }
}
