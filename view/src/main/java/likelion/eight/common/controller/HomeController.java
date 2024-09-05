package likelion.eight.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import likelion.eight.domain.token.service.TokenCookieService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//@Controller
public class HomeController {
    private final TokenCookieService cookieservice;

    // will fix asap.
    public HomeController(TokenCookieService tokenCookieService) {
        this.cookieservice = tokenCookieService;
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        boolean isLoggedIn = cookieservice.isUserLoggedIn(request);
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "index";
    }
}