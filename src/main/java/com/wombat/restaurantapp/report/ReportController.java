package com.wombat.restaurantapp.report;

import com.wombat.restaurantapp.menu.MenuItem;
import com.wombat.restaurantapp.menu.MenuItemDAO;
import com.wombat.restaurantapp.order.Order;
import com.wombat.restaurantapp.order.OrderDAO;
import com.wombat.restaurantapp.order.OrderItem;
import com.wombat.restaurantapp.order.OrderStatus;
import com.wombat.restaurantapp.reservation.Reservation;
import com.wombat.restaurantapp.reservation.ReservationDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ReportController {
    private final OrderDAO orderDAO;
    private final MenuItemDAO menuItemDAO;
    private final ReservationDAO reservationDAO;

    public ReportController(OrderDAO orderDAO, MenuItemDAO menuItemDAO, ReservationDAO reservationDAO) {
        this.orderDAO = orderDAO;
        this.menuItemDAO = menuItemDAO;
        this.reservationDAO = reservationDAO;
    }

    @GetMapping("/reports")
    public String index(Model model) {
        Iterable<Order> orders = orderDAO.findAll();
        Iterable<MenuItem> menuItems = menuItemDAO.findAll();
        Iterable<Reservation> reservations = reservationDAO.findAll();

        model.addAttribute("orders", orders);
        model.addAttribute("totalNumberItemsSold", getTotalNumberItemsSold(orders).toString());
        model.addAttribute("totalCostItemsSold", getTotalCostItemsSold(orders).toString());
        model.addAttribute("totalNumberOrders", getTotalNumberOrders(orders).toString());
        model.addAttribute("totalCancellations", getTotalCancellations(orders).toString());
        model.addAttribute("totalReservations", getTotalReservations(reservations));
        model.addAttribute("topItems", getTopItems(menuItems));

        return "reports";
    }

    public Integer getTotalNumberItemsSold(Iterable<Order> orders){
        Integer count = 0;
        for (Order order : orders){
            for(OrderItem item : order.getOrderItems()){
                count += item.getQuantity();
            }
        }
        return count;
    }

    public Double getTotalCostItemsSold(Iterable<Order> orders){
        Double count = 0.0;
        for (Order order : orders){
            count += order.getTotal();
        }
        return count;
    }

    public Integer getTotalNumberOrders(Iterable<Order> orders) {
        Integer count = 0;
        for (Order order : orders){
            count += 1;
        }
        return count;
    }

    public Integer getTotalCancellations(Iterable<Order> orders) {
        Integer count = 0;
        for (Order order : orders){
            if (order.getStatus() == OrderStatus.CANCELLED){
                count += 1;
            }
        }
        return count;
    }

    public Integer getTotalReservations(Iterable<Reservation> reservations) {
        Integer count = 0;
        for (Reservation reservation : reservations) {
            count += 1;
        }
        return count;
    }

    public List<TopItem> getTopItems(Iterable<MenuItem> menuItems) {
        List<TopItem> items = new ArrayList<>();
        for(MenuItem item : menuItems) {
            TopItem topItem = new TopItem(item, 0);
            for(OrderItem orderItem : item.getOrderItems()) {
                if (orderItem.getOrder().getStatus() == OrderStatus.COMPLETED){
                    topItem.setCount(topItem.getCount() + orderItem.getQuantity());
                }
            }

            if(topItem.getCount() > 0) {
                items.add(topItem);
            }
        }

        items.sort(Comparator.comparing(TopItem::getCount).reversed());

        return items;
    }
}
