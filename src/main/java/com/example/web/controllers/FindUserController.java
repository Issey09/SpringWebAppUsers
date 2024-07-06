package com.example.web.controllers;


import com.example.web.models.User;
import com.example.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class FindUserController {
    @Autowired
    private UserService userService;


    @GetMapping("/search")
    public String search(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Проверяем, аутентифицирован ли пользователь
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"));

        // Добавляем атрибут isAuthenticated в модель
        model.addAttribute("isAuthenticated", isAuthenticated);
        return "finduser/find";
    }


    @PostMapping("/search")
    public String findUser(@RequestParam long Id, Model model) {
        Optional<User> user = userService.getUserById(Id);
        model.addAttribute("user", user);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Проверяем, аутентифицирован ли пользователь
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"));

        // Добавляем атрибут isAuthenticated в модель
        model.addAttribute("isAuthenticated", isAuthenticated);

        // Если пользователь аутентифицирован, добавляем имя пользователя в модель
        if (isAuthenticated) {
            model.addAttribute("username", authentication.getName());
        }
        if (user.isPresent()) {
            return "finduser/sucsess";
        }else {
            return "finduser/fail";
        }
    }

}
