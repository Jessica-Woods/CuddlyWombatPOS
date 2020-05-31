package com.wombat.restaurantapp.employee;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    // Other data about an employee could be added here such as hourly rate or hours.
    protected Employee() {}

    public Employee(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public String getName() { return name; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
}
