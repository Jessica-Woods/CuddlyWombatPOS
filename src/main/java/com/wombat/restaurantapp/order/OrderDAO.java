package com.wombat.restaurantapp.order;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface OrderDAO extends CrudRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.status = 'IN_KITCHEN' ")
    Collection<Order> findKitchenOrders();

    @Query("SELECT o FROM Order o WHERE o.status = 'PENDING' OR o.status = 'IN_KITCHEN'")
    Collection<Order> findActiveOrders();

    @Query("SELECT o FROM Order o WHERE o.status = 'COMPLETED' OR o.status = 'CANCELLED'")
    Collection<Order> findInActiveOrders();
}
