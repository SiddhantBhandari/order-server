package com.sbmicroservices.libs.orders;


import com.sbmicroservices.libs.BaseEntity;
import com.sbmicroservices.libs.users.Users;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;

@Entity
@Table(name = "cart_orders")
@SQLDelete(sql = "UPDATE cart_orders SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Orders extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @Column(name = "order_id", unique = true, nullable = false)
    private String orderId;

    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    @Column(name = "delivery_date", nullable = false)
    private Date deliveryDate;


    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;


    private String currency;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
