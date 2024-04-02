package io.mountblue.reddit.redditClone.controller;

import io.mountblue.reddit.redditClone.dto.UserDto;
import io.mountblue.reddit.redditClone.model.Topic;
import io.mountblue.reddit.redditClone.model.User;
import io.mountblue.reddit.redditClone.service.TopicService;
import io.mountblue.reddit.redditClone.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserRegistrationController {

    private final UserService userService;
    private final TopicService topicService;

    @Autowired
    public UserRegistrationController(UserService userService, TopicService topicService) {
        this.userService = userService;
        this.topicService = topicService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        List<Topic> topics = topicService.getAllTopics();
        model.addAttribute("topics", topics);
        return "user-registration";
    }

    @PostMapping("/user/new")
    public String registerUser(@ModelAttribute("userDto") @Valid UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Topic> topics = topicService.getAllTopics();
            model.addAttribute("topics", topics);
            return "user-registration";
        }

        if (userService.existsByUsername(userDto.getUsername())) {
            bindingResult.rejectValue("username", "error.userDto", "Username already taken");
        }

        if (userService.existsByEmail(userDto.getEmail())) {
            bindingResult.rejectValue("email", "error.userDto", "Account already exists");
        }

        if (bindingResult.hasErrors()) {
            List<Topic> topics = topicService.getAllTopics();
            model.addAttribute("topics", topics);
            return "user-registration";
        }

        List<Topic> interests = userDto.getInterestIds().stream()
                .map(topicId -> topicService.findById(topicId))
                .collect(Collectors.toList());

        userDto.setInterests(interests);

        User user = userService.mapDtoToEntity(userDto);
        userService.createUser(user);
        return "user-registration";
    }
}
