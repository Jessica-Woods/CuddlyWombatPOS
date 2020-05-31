package com.wombat.restaurantapp.reservation;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String phoneNumber;
    private int numberOfPeople;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime time;

    protected Reservation(){}

    public Reservation(String name, String phoneNumber, int numberOfPeople, LocalDate date, LocalTime time) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.numberOfPeople = numberOfPeople;
        this.date = date;
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format(
                "Reservation[id=%d, name='%s', phoneNumber='%s', numberOfPeople=%d]",
                id, name, phoneNumber, numberOfPeople);
    }

    // Getters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getAESTDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/y");
        return date.format(formatter);
    }

    public String getAESTTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return time.format(formatter);
    }

    // Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
