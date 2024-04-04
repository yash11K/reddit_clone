package io.mountblue.reddit.redditClone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class LoginController {
    @GetMapping("/login-page")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/")
    public String redirect(){
        return "redirect:/login-page";
    }
}
