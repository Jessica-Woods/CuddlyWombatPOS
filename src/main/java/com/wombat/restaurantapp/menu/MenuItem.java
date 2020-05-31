package com.wombat.restaurantapp.menu;

import com.wombat.restaurantapp.order.OrderItem;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "menu")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String itemName;

    private double itemCost;

    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    protected MenuItem() {}
    public MenuItem(String itemName, double itemCost){
        this.itemName = itemName;
        this.itemCost = itemCost;
    }

    @Override
    public String toString() {
        return String.format(
                "Order[id=%d, item='%s', cost='%s']",
                id, itemName, itemCost);
    }

    // Getters
    public Long getId() { return id; }
    public String getItemName() { return itemName; }
    public double getItemCost() { return itemCost; }
    public String getItemCostString() {
        return String.format("$%.2f", itemCost);
    }
    public List<OrderItem> getOrderItems() { return orderItems; }

    // Setters
    public void update(MenuItem other) {
        itemName = other.itemName;
        itemCost = other.itemCost;
    }
}
