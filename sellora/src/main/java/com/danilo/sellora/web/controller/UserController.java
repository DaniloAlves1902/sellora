package com.danilo.sellora.web.controller;

import com.danilo.sellora.application.service.UserService;
import com.danilo.sellora.domain.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> listUsers() {
        return userService.listUsers();
    }

    @GetMapping("/{email}")
    public List<User> listUsersByEmail(String email) {
        return userService.listUserByEmail(email);
    }
}
