package com.sbmicroservices.controller;


import com.sbmicroservices.exceptions.InvalidAttributesException;
import com.sbmicroservices.exceptions.OrderNotFoundException;
import com.sbmicroservices.exceptions.UserNotFoundException;
import com.sbmicroservices.libs.orders.OrderService;
import com.sbmicroservices.libs.orders.Orders;
import com.sbmicroservices.models.request.PlaceOrderRequest;
import com.sbmicroservices.models.response.OrderDetails;
import com.sbmicroservices.models.response.OrderResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/orders", produces = "application/json")
@Log4j2
public class OrderController {


    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/placeOrder")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody PlaceOrderRequest request) throws UserNotFoundException, InvalidAttributesException {
        Orders orders = orderService.placeOrder(request);
        return ResponseEntity.ok(new OrderResponse(orders.getOrderId(), orders.getId(), "Order placed successfully", true));
    }


    @GetMapping("/details/{id}")
    public ResponseEntity<OrderDetails> getOrderDetails(@PathVariable("id") String id) throws OrderNotFoundException {
        return ResponseEntity.ok(orderService.getOrderDetails(Long.valueOf(id)));
    }

}
