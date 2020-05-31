package com.wombat.restaurantapp.payment;

public interface PaymentProcessor {
    public void chargeCustomer(Double amount);
}
