package com.sbmicroservices.libs.cart_items;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemsService {

    private final CartItemRepository cartItemRepository;


    public CartItemsService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public void save(CartItems items){
        cartItemRepository.save(items);
    }

    public List<CartItems> getOrderedItems(Long orderId){
        return cartItemRepository.getByOrder(orderId);
    }
}
