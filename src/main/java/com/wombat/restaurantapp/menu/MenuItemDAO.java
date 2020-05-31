package com.wombat.restaurantapp.menu;

import org.springframework.data.repository.CrudRepository;

public interface MenuItemDAO extends CrudRepository<MenuItem, Long> {
}