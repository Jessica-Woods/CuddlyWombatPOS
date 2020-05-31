package com.wombat.restaurantapp.reservation;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReservationDAO extends PagingAndSortingRepository<Reservation, Long> {
}
