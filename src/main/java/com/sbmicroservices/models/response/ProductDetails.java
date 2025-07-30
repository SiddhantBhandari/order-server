package com.sbmicroservices.models.response;


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

    private String category;
    private String productId;

    private Vendor vendor;


    public ProductDetails(com.sbmicroservices.apis.product_server.response.ProductDetails productDetails) {
        this.id = productDetails.getId();
        this.productCode = productDetails.getProductCode();
        this.productName = productDetails.getProductName();
        this.createdAt = productDetails.getCreatedAt();
        this.updatedAt = productDetails.getUpdatedAt();
        this.price = productDetails.getPrice();
        this.vendorId = productDetails.getVendorId();
        this.category = productDetails.getCategory();
        this.productId = productDetails.getProductId();
        if(productDetails.getVendor() != null){
            this.vendor = new Vendor(productDetails.getVendor());
        }
    }
}
