package com.wombat.restaurantapp.order;

import com.wombat.restaurantapp.employee.Employee;
import com.wombat.restaurantapp.employee.EmployeeDAO;
import com.wombat.restaurantapp.menu.MenuItem;
import com.wombat.restaurantapp.menu.MenuItemDAO;
import com.wombat.restaurantapp.payment.PaymentProcessor;
import com.wombat.restaurantapp.printing.ReceiptPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrderController {
    private final OrderDAO orderDAO;
    private final MenuItemDAO menuItemDAO;
    private final EmployeeDAO employeeDAO;
    private final PaymentProcessor paymentProcessor;
    private final ReceiptPrinter receiptPrinter;

    @Autowired
    public OrderController(
            OrderDAO orderDAO, MenuItemDAO menuItemDAO, EmployeeDAO employeeDAO,
            PaymentProcessor paymentProcessor, ReceiptPrinter receiptPrinter
    ) {
        this.orderDAO = orderDAO;
        this.menuItemDAO = menuItemDAO;
        this.employeeDAO = employeeDAO;
        this.paymentProcessor = paymentProcessor;
        this.receiptPrinter = receiptPrinter;
    }

    @GetMapping("/")
    public RedirectView root() {
        return new RedirectView("/orders");
    }

    @GetMapping("/orders")
    public String index(Model model) {
        Collection<Order> activeOrders = orderDAO.findActiveOrders();
        List<Order> sortedActiveOrders = activeOrders
                .stream()
                .sorted(Comparator.comparing(Order::getStatus))
                .collect(Collectors.toList());

        model.addAttribute("activeOrders", sortedActiveOrders);

        Collection<Order> inActiveOrders = orderDAO.findInActiveOrders();
        List<Order> sortedInActiveOrders = inActiveOrders
                .stream()
                .sorted(Comparator.comparing(Order::getStatus))
                .collect(Collectors.toList());

        model.addAttribute("inActiveOrders", sortedInActiveOrders);

        Iterable<Employee> employees = employeeDAO.findAll();
        model.addAttribute("employees", employees);

        return "orders";
    }

    @PostMapping("/orders")
    public RedirectView create(@ModelAttribute Order order) {
        Order savedOrder = orderDAO.save(order);
        return new RedirectView("/orders/" + savedOrder.getId());
    }

    @GetMapping("/orders/{orderId}")
    public String get(@PathVariable("orderId") Long id, Model model) {
        Order order = orderDAO.findById(id).get();
        model.addAttribute("order", order);

        Iterable<MenuItem> menuItems = menuItemDAO.findAll();
        model.addAttribute("menuItems", menuItems);

        return "edit_order";
    }

    @GetMapping("/orders/{orderId}/pay")
    public String pay(@PathVariable("orderId") Long id, Model model) {
        Order order = orderDAO.findById(id).get();
        paymentProcessor.chargeCustomer(order.getTotal());

        receiptPrinter.print(order);

        model.addAttribute("orderId", id);
        return "pay_order";
    }

    @GetMapping("/orders/{orderId}/set-status")
    public RedirectView setStatus(@PathVariable("orderId") Long id, @RequestParam OrderStatus newStatus) {
        Order order = orderDAO.findById(id).get();

        order.setStatus(newStatus);
        orderDAO.save(order);

        return new RedirectView("/orders");
    }

    @GetMapping("/orders/{orderId}/add-menu-item")
    public RedirectView addMenuItem(@PathVariable("orderId") Long id, @RequestParam Long menuItemId) {
        Order order = orderDAO.findById(id).get();
        MenuItem menuItem = menuItemDAO.findById(menuItemId).get();

        order.addMenuItem(menuItem);
        orderDAO.save(order);

        return new RedirectView("/orders/" + order.getId());
    }

    @GetMapping("/orders/{orderId}/delete-menu-item")
    public RedirectView deleteMenuItem(@PathVariable("orderId") Long id, @RequestParam Long menuItemId) {
        Order order = orderDAO.findById(id).get();
        MenuItem menuItem = menuItemDAO.findById(menuItemId).get();

        order.deleteMenuItem(menuItem);
        orderDAO.save(order);

        return new RedirectView("/orders/" + order.getId());
    }

    @PostMapping("/orders/{orderId}/add-note")
    public RedirectView addOrderNote(@PathVariable("orderId") Long id, @RequestParam String orderNote) {
        Order order = orderDAO.findById(id).get();
        order.setOrderNotes(orderNote);
        orderDAO.save(order);
        return new RedirectView("/orders/" + order.getId());
    }
}