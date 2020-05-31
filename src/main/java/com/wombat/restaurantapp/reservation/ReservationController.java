package com.wombat.restaurantapp.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ReservationController {
    private final ReservationDAO reservationDAO;

    @Autowired
    public ReservationController(ReservationDAO reservationDAO){
        this.reservationDAO = reservationDAO;
    }

    @GetMapping("/reservations")
    public String index(Model model){
        Iterable<Reservation> reservations = reservationDAO.findAll(Sort.by("date","time").ascending());
        model.addAttribute("reservations", reservations);
        return "reservations";
    }

    @PostMapping("/reservations")
    public RedirectView createNewReservation(@ModelAttribute Reservation reservation){
        reservationDAO.save(reservation);
        return new RedirectView("/reservations");
    }

    @GetMapping("/reservations/{reservationId}/delete")
    public RedirectView deleteReservation(@PathVariable("reservationId") Long id) {
        reservationDAO.deleteById(id);
        return new RedirectView("/reservations");
    }
}