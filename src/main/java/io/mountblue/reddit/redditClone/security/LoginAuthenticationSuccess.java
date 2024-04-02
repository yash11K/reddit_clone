package io.mountblue.reddit.redditClone.security;

import io.mountblue.reddit.redditClone.model.User;
import io.mountblue.reddit.redditClone.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class LoginAuthenticationSuccess implements AuthenticationSuccessHandler {
    private final UserService userService;

    @Autowired
    public LoginAuthenticationSuccess(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, IOException {
        String userName = authentication.getName();
        User user = userService.findByUsername(userName);
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        response.sendRedirect(request.getContextPath() + "/home");
    }

}
