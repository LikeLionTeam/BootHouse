package likelion.eight.common.controller;

import likelion.eight.common.service.CookieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class NavbarController {

    private final CookieService cookieservice;

    public NavbarController(CookieService cookieService) {
        this.cookieservice = cookieService;
    }

    @ModelAttribute("isLoggedIn")
    public boolean isLoggedIn(HttpServletRequest request) {
        return cookieservice.isUserLoggedIn(request);
    }
}