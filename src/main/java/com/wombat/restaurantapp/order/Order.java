package com.wombat.restaurantapp.order;

import com.wombat.restaurantapp.menu.MenuItem;

import javax.persistence.*;
import java.util.List;

@Entity// - entity cant have containers
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
    public void setStatus(OrderStatus status) { this.status = status; }


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
}
