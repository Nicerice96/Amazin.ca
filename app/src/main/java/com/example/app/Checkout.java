package com.example.app;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Checkout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Cart cart;
    private LocalDateTime checkoutTime;
    private double totalAmount;
    private enum paymentStatus{
        ACCEPTED,
        DENIED,
        PENDING,
        PROCESSING,
        BYPASS // not in a moment of payments
    }
    private enum orderStatus{
        ORDERED,
        IN_CART,
        PROCESSING,
        BYPASS // not in a moment of ordering
    }

    public Checkout(){

    }

    public Checkout(Cart cart, LocalDateTime checkoutTime, double totalAmount) {
        this.cart = cart;
        this.checkoutTime = checkoutTime;
        this.totalAmount = totalAmount;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public LocalDateTime getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(LocalDateTime checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
