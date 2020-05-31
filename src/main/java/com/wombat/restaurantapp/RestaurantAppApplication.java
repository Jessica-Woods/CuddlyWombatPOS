package com.wombat.restaurantapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class RestaurantAppApplication {
    private static final Logger logger = LoggerFactory.getLogger(RestaurantAppApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RestaurantAppApplication.class, args);
    }

    @PostConstruct
    public void init() {
        logger.info("Set default timezone to Australia/Sydney");
        TimeZone.setDefault(TimeZone.getTimeZone("Australia/Sydney"));
    }
}