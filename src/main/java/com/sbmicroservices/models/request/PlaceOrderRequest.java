package com.sbmicroservices.models.request;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlaceOrderRequest {

    private Long userId; // Temporary | to be fetched from token later.

    private String currency;

    private List<Items> items = new ArrayList<>();

    private String paymentMode;

}
