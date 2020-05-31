package com.wombat.restaurantapp.printing;

import com.wombat.restaurantapp.order.Order;

public interface ReceiptPrinter {
    void print(Order order);
}
