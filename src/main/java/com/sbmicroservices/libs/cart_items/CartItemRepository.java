package com.sbmicroservices.libs.cart_items;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Long> {


    @Query(value = "SELECT ct FROM CartItems ct WHERE ct.order.id = :orderId", nativeQuery = false)
    List<CartItems> getByOrder(Long orderId);
}
