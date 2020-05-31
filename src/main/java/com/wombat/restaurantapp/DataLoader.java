package com.wombat.restaurantapp;

import com.wombat.restaurantapp.employee.Employee;
import com.wombat.restaurantapp.employee.EmployeeDAO;
import com.wombat.restaurantapp.menu.MenuItem;
import com.wombat.restaurantapp.menu.MenuItemDAO;
import com.wombat.restaurantapp.reservation.Reservation;
import com.wombat.restaurantapp.reservation.ReservationDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class DataLoader implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private MenuItemDAO menuItemDAO;
    private ReservationDAO reservationDAO;
    private EmployeeDAO employeeDAO;

    @Autowired
    public DataLoader(MenuItemDAO menuItemDAO, ReservationDAO reservationDAO, EmployeeDAO employeeDAO) {
        this.menuItemDAO = menuItemDAO;
        this.reservationDAO = reservationDAO;
        this.employeeDAO = employeeDAO;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Seeding MenuItem data");
        menuItemDAO.save(new MenuItem("Latte", 5.00));
        menuItemDAO.save(new MenuItem("Tall Black", 2.50));
        menuItemDAO.save(new MenuItem("Chai", 3.00));
        menuItemDAO.save(new MenuItem("Hamburger", 15.00));
        menuItemDAO.save(new MenuItem("Fish & Chips", 27.00));
        menuItemDAO.save(new MenuItem("Steak", 33.33));

        reservationDAO.save(new Reservation("Anne","0497979797", 3, LocalDate.now(), LocalTime.now()));

        employeeDAO.save(new Employee("Anne"));
        employeeDAO.save(new Employee("Bill"));
        employeeDAO.save(new Employee("Cathy"));
        employeeDAO.save(new Employee("Dean"));
    }
}
