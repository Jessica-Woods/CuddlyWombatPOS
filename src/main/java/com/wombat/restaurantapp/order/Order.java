package com.wombat.restaurantapp.order;

import com.wombat.restaurantapp.menu.MenuItem;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity// - entity cant have containers
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private ZonedDateTime createdTime;
    private ZonedDateTime kitchenTime;
    private ZonedDateTime completedTime;

    private String employee;

    private Boolean pickUpOrder;

    private String destination;

    private String orderNotes;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    protected Order() {}

    public Order(String employee, Boolean pickUpOrder, String destination, String orderNotes) {
        this.createdTime = ZonedDateTime.now();
        this.employee = employee;
        this.pickUpOrder = pickUpOrder;
        this.destination = destination;
        this.orderNotes = orderNotes;
        this.status = OrderStatus.PENDING;
    }

    @Override
    public String toString() {
        return String.format(
                "Order[id=%d, employee='%s', total='%s']",
                id, employee);
    }

    // Getters
    public Long getId() { return id; }
    public String getEmployee() { return employee; }
    public String getPickUpOrder() {
        if (pickUpOrder == null){
            return "no";
        }
        return "yes";
    }

    public ZonedDateTime getCreatedTime() { return createdTime; }
    public ZonedDateTime getKitchenTime() { return kitchenTime; }
    public ZonedDateTime getCompletedTime() { return completedTime; }
    public String getDestination() { return destination; }
    public List<OrderItem> getOrderItems() { return orderItems; }
    public Double getTotal() {
        return orderItems.stream().mapToDouble(orderItem -> orderItem.cost()).sum();
    }
    public String getTotalString() {
        return String.format("$%.2f", getTotal());
    }
    public String getOrderNotes() { return orderNotes; }
    public OrderStatus getStatus() { return status; }

    public void setEmployee(String employee) { this.employee = employee; }
    public void setPickUpOrder(Boolean pickUpOrder) { this.pickUpOrder = pickUpOrder; }
    public void setDestination(String destination) { this.destination = destination; }
    public void setOrderNotes(String orderNotes) {this.orderNotes = orderNotes;}

    public void setStatus(OrderStatus status) {
        if(this.status != status) {
            if(status == OrderStatus.IN_KITCHEN) { this.kitchenTime = ZonedDateTime.now(); }
            if(status == OrderStatus.COMPLETED || status == OrderStatus.CANCELLED) {
                this.completedTime = ZonedDateTime.now();
            }
        }

        this.status = status;
    }

    public void addMenuItem(MenuItem item) {
        // Orders can only be edited if they are not completed or cancelled
        if(status != OrderStatus.PENDING && status != OrderStatus.IN_KITCHEN) {
            return;
        }

        for(OrderItem orderItem : orderItems) {
            if(orderItem.menuItem.getId() == item.getId()) {
                orderItem.addQuantity(1);
                return;
            }
        }

        orderItems.add(new OrderItem(this, item, 1));
    }

    public void deleteMenuItem(MenuItem item) {
        // Orders can only be edited if they are not completed or cancelled
        if(status != OrderStatus.PENDING && status != OrderStatus.IN_KITCHEN) {
            return;
        }

        for(OrderItem orderItem : orderItems) {
            if(orderItem.menuItem.getId() == item.getId()) {
                orderItem.reduceQuantity(1);
                if(orderItem.getQuantity() <= 0) {
                    orderItems.remove(orderItem);
                }
                return;
            }
        }
    }

    public String getCreatedTimeString() {
        if (createdTime == null){ return ""; }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return createdTime.format(formatter);
    }

    public String getKitchenTimeString() {
        if (kitchenTime == null){ return ""; }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return kitchenTime.format(formatter);
    }

    public String getCompletedTimeString() {
        if (completedTime == null){ return ""; }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return completedTime.format(formatter);
    }
}
