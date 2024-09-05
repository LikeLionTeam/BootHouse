package likelion.eight.common.controller;

import likelion.eight.domain.token.service.TokenCookieService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class NavbarController {

    private final TokenCookieService cookieservice;

    public NavbarController(TokenCookieService tokenCookieService) {
        this.cookieservice = tokenCookieService;
    }

    @ModelAttribute("isLoggedIn")
    public boolean isLoggedIn(HttpServletRequest request) {
        return cookieservice.isUserLoggedIn(request);
    }
}