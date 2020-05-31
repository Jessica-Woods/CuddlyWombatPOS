package com.wombat.restaurantapp.kitchen;

import com.wombat.restaurantapp.order.Order;
import com.wombat.restaurantapp.order.OrderDAO;
import com.wombat.restaurantapp.order.OrderStatus;
import com.wombat.restaurantapp.printing.ReceiptPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.PrimitiveIterator;

@Controller
public class KitchenController {
    private final OrderDAO orderDAO;

    @Autowired
    public KitchenController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @GetMapping("/kitchen")
    public String index(Model model) {
        Iterable<Order> orders = orderDAO.findKitchenOrders();
        model.addAttribute("orders", orders);

        return "kitchen";
    }

    @GetMapping("/kitchen/{orderId}/set-status")
    public RedirectView setStatus(@PathVariable("orderId") Long id, @RequestParam OrderStatus newStatus) {
        Order order = orderDAO.findById(id).get();
        order.setStatus(newStatus);
        orderDAO.save(order);

        return new RedirectView("/kitchen");
    }

}