package com.sbmicroservices.apis.product_server.response;


import lombok.Data;

@Data
public class ProductDetails {

    private Long id;

    private String productCode;

    private String productName;

    private Long createdAt;

    private Long updatedAt;

    private Double price;

    private Long vendorId;

    private Double availableQuantity;

    private String category;
    private String productId;

    private VendorDetails vendor;
}
