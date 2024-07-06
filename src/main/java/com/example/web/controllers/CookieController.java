package com.example.web.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CookieController {

    @GetMapping("/delete")
    public String deleteCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0); // Установка времени жизни куки в 0, что удаляет его
        cookie.setPath("/"); // Путь должен совпадать с путем куки, который вы хотите удалить
        response.addCookie(cookie);
        return "redirect:/"; // Перенаправление на главную страницу или другую страницу
    }
}