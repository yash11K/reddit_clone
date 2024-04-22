package io.mountblue.reddit.redditClone.controller;

import io.mountblue.reddit.redditClone.model.Role;
import io.mountblue.reddit.redditClone.repository.RoleRepository;
import io.mountblue.reddit.redditClone.restcontroller.TopicController;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class LoginController {
    RoleRepository roleRepository;
    TopicController topicController;

    @GetMapping("/login-page")

    public String loginPage() {
        return "login";

    }

    @GetMapping("/")
    public String redirect(){
        return "redirect:/login-page";
    }
}
