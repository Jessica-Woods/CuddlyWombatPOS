package com.wombat.restaurantapp.employee;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeDAO extends CrudRepository<Employee, Long> {
}
