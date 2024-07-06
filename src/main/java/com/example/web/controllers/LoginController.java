package com.example.web.controllers;

import com.example.web.models.User;
import com.example.web.repository.UserRepository;
import com.example.web.service.JwtService;
import com.example.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;


    @GetMapping("/log")
    public String main(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Проверяем, аутентифицирован ли пользователь
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"));

        // Добавляем атрибут isAuthenticated в модель
        model.addAttribute("isAuthenticated", isAuthenticated);
        return "login/login";
    }

    @PostMapping("/log")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        Optional<User> user = userService.findUserbyUsername(username);

        boolean isAuntificated = passwordEncoder.matches(password, user.get().getPassword());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Проверяем, аутентифицирован ли пользователь
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"));

        // Добавляем атрибут isAuthenticated в модель
        model.addAttribute("isAuthenticated", isAuthenticated);

        // Если пользователь аутентифицирован, добавляем имя пользователя в модель

        if (isAuntificated) {
            return "login/sucsess";
        }else{
            return "login/fail";
        }
}}
