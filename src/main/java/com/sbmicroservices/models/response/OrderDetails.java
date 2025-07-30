package com.sbmicroservices.models.response;

import com.sbmicroservices.libs.orders.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor(staticName = "builder")
public class OrderDetails {

    private Long id;

    private Long createdAt;

    private Long updatedAt;

    private String orderId;

    private Long orderDate;

    private Long deliveryDate;

    private Double totalAmount;

    private String orderStatus;

    private List<Items> items = new ArrayList<>();

    private UserDetails user;

    public OrderDetails(Orders orders) {
        this.id = orders.getId();
        this.createdAt = orders.getCreatedAt().getTime();
        this.updatedAt = orders.getUpdatedAt() != null ? orders.getUpdatedAt().getTime() : null;
        this.orderId = orders.getOrderId();
        this.orderDate = orders.getOrderDate() != null ? orders.getOrderDate().getTime() : null;
        this.deliveryDate = orders.getDeliveryDate() != null ? orders.getDeliveryDate().getTime() : null;
        this.totalAmount = orders.getTotalAmount() != null ? orders.getTotalAmount() : null;
        this.orderStatus = orders.getStatus() != null ? orders.getStatus().toString() : null;
        this.user = new UserDetails(orders.getUser());
    }
}
