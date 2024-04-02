package io.mountblue.reddit.redditClone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class LoginController {
    @GetMapping("/customlogin")
    public String loginPage() {
        return "login";
    }
}
