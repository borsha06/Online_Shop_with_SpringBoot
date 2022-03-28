package com.dsam.assignment01.controllers;
import com.dsam.assignment01.models.Order;
import com.dsam.assignment01.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsers(Model model) {

        model.addAttribute("users",this.userService.findAll());
        return "users";
    }

    @GetMapping("/{userId}/orders")
    public String getOrdersByUserId(@PathVariable(value = "userId") String userId, Model model) {
        String username = this.userService.findUsernameByUserId(userId);
        List<Order> orders = this.userService.findOrdersByUser(username);

        model.addAttribute("orders", orders);
        return "orders";
    }
}
