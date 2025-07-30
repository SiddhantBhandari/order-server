package com.sbmicroservices.models.response;


import com.sbmicroservices.apis.product_server.response.GetProductResponse;
import com.sbmicroservices.libs.cart_items.CartItems;
import lombok.Data;

@Data
public class Items {

    private Long id;

    private Long createdAt;

    private Long updatedAt;

    private Long productId;

    private Double quantity;

    private String productName;

    private Double unitPrice;

    private Double totalPrice;

    private String itemId;

    private Long vendorId;

    private ProductDetails productDetails;


    public Items(CartItems items, GetProductResponse productResponse) {
        this.id = items.getId();
        this.createdAt = items.getCreatedAt().getTime();
        this.updatedAt = items.getUpdatedAt() != null ? items.getUpdatedAt().getTime() : null;
        this.productId = items.getProductId();
        this.quantity = items.getQuantity();
        this.productName = items.getProductName();
        this.unitPrice = items.getUnitPrice();
        this.totalPrice = items.getTotalPrice();
        this.itemId = items.getItemId();
        this.vendorId = items.getVendorId();
        if(productResponse != null){
            this.productDetails = new ProductDetails(productResponse.getDetails());
        }
    }
}
