package com.wombat.restaurantapp.printing;

import com.wombat.restaurantapp.order.Order;
import com.wombat.restaurantapp.order.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class FakeReceiptPrinter implements ReceiptPrinter {
    @Override
    public void print(Order order) {
        System.out.println("=== Receipt (Order Number: " + order.getId() + ") ===");
        for(OrderItem item : order.getOrderItems()) {
            System.out.println(item.costString() + " - " + item.getQuantity() + "x " + item.getMenuItem().getItemName());
        }
        System.out.println();
        System.out.println("Total: " + order.getTotalString());
        System.out.println("Waiter: " + order.getEmployee());
        System.out.println("====================================");
    }
}
