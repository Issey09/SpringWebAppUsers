package com.example.web.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HelloRestController {
    @GetMapping("/api/v1/hi")
    public String  hello() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();


    }
}
