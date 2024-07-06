package com.example.web.controllers;



import com.example.web.models.User;
import com.example.web.repository.UserRepository;
import com.example.web.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/reg")
    public String main(Model model) {
        model.addAttribute("user", new User());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Проверяем, аутентифицирован ли пользователь
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"));

        // Добавляем атрибут isAuthenticated в модель
        model.addAttribute("isAuthenticated", isAuthenticated);

        return "registration/reg";
    }

    @PostMapping("/reg")
    public String registerUser(@ModelAttribute("user") User user, HttpServletResponse response, Model model) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean UsernameinBb = userRepository.findByUsername(user.getUsername()).isPresent();
        if (UsernameinBb == true) {
            model.addAttribute("UsernameinBb", "yes");
            return "registration/reg";
        } else {
            String token = jwtService.generateToken(user.getUsername());

        // Add JWT to cookie
            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            userRepository.save(user);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Проверяем, аутентифицирован ли пользователь
            boolean isAuthenticated = authentication != null && authentication.isAuthenticated()
                    && !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"));

            // Добавляем атрибут isAuthenticated в модель
            model.addAttribute("isAuthenticated", isAuthenticated);


            return "registration/sucsess";
        }
    }
}