package io.mountblue.reddit.redditClone.controller;

import io.mountblue.reddit.redditClone.model.Role;
import io.mountblue.reddit.redditClone.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@AllArgsConstructor
public class LoginController {
    RoleRepository roleRepository;

    @GetMapping("/login-page")

    public String loginPage() {
        roleRepository.save(Role.builder().id(1L).name("ROLE_REDDITOR").build());
        return "login";

    }

    @GetMapping("/")
    public String redirect(){
        return "redirect:/login-page";
    }
}
