package likelion.eight.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import likelion.eight.common.service.CookieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//@Controller
public class HomeController {
    private final CookieService cookieservice;

    // will fix asap.
    public HomeController(CookieService cookieService) {
        this.cookieservice = cookieService;
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        boolean isLoggedIn = cookieservice.isUserLoggedIn(request);
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "index";
    }
}