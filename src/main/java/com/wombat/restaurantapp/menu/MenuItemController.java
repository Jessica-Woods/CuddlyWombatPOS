package com.wombat.restaurantapp.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class MenuItemController {
    private final MenuItemDAO menuItemDAO;

    @Autowired
    public MenuItemController(MenuItemDAO menuItemDAO){
        this.menuItemDAO = menuItemDAO;
    }

    @GetMapping("/menu-items")
    public String index(Model model){
        Iterable<MenuItem> menuItems = menuItemDAO.findAll();
        model.addAttribute("menuItems", menuItems);
        return "menu_items";
    }

    @PostMapping("/menu-items")
    public RedirectView createNewMenuItem(@ModelAttribute MenuItem menuItem){
        menuItemDAO.save(menuItem);
        return new RedirectView("/menu-items");
    }

    @GetMapping("/menu-items/{menuItemId}/delete")
    public RedirectView deleteMenuItem(@PathVariable("menuItemId") Long id) {
        menuItemDAO.deleteById(id);
        return new RedirectView("/menu-items");
    }

    @PostMapping("/menu-items/{menuItemId}/edit")
    public RedirectView updateMenuItem(@PathVariable("menuItemId") Long id, @ModelAttribute MenuItem newMenuItem) {
        MenuItem menuItem = menuItemDAO.findById(id).get();
        menuItem.update(newMenuItem);
        menuItemDAO.save(menuItem);

        return new RedirectView("/menu-items");
    }
}
