package com.wombat.restaurantapp.payment;

import org.springframework.stereotype.Component;

@Component
public class FakePaymentProcessor implements PaymentProcessor {
    @Override
    public void chargeCustomer(Double amount) {
        System.out.println("Charging customer: $" + amount);
    }
}
