package com.wombat.restaurantapp.order;

import com.wombat.restaurantapp.menu.MenuItem;

import javax.persistence.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    MenuItem menuItem;

    private Integer quantity;

    protected OrderItem() {}

    public OrderItem(Order order, MenuItem menuItem, Integer quantity) {
        this.order = order;
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public void addQuantity(Integer amount) {
        quantity += amount;
    }

    public void reduceQuantity(Integer amount) {
        quantity -= amount;
        if(quantity < 0) {
            quantity = 0;
        }
    }

    public Double cost() {
        return menuItem.getItemCost() * quantity;
    }

    public String costString() {
        return String.format("$%.2f", cost());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer count) {
        this.quantity = count;
    }
}
