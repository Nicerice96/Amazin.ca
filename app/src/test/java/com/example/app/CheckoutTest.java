package com.example.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutTest {

    private Checkout checkout;
    private Cart cart;
    private LocalDateTime testTime;
    private double testAmount;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        testTime = LocalDateTime.now();
        testAmount = 100.50;
        checkout = new Checkout(cart, testTime, testAmount);
    }

    @Test
    void getCart() {
        assertEquals(cart, checkout.getCart());
    }

    @Test
    void setCart() {
        Cart newCart = new Cart();
        checkout.setCart(newCart);
        assertEquals(newCart, checkout.getCart());
    }

    @Test
    void getCheckoutTime() {
        assertEquals(testTime, checkout.getCheckoutTime());
    }

    @Test
    void setCheckoutTime() {
        LocalDateTime newTime = LocalDateTime.now().plusDays(1);
        checkout.setCheckoutTime(newTime);
        assertEquals(newTime, checkout.getCheckoutTime());
    }

    @Test
    void getTotalAmount() {
        assertEquals(testAmount, checkout.getTotalAmount());
    }

    @Test
    void setTotalAmount() {
        double newAmount = 200.75;
        checkout.setTotalAmount(newAmount);
        assertEquals(newAmount, checkout.getTotalAmount());
    }
}
