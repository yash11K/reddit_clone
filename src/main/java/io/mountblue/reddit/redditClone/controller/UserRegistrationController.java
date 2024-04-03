package io.mountblue.reddit.redditClone.controller;

import io.mountblue.reddit.redditClone.dto.UserDto;
import io.mountblue.reddit.redditClone.model.Role;
import io.mountblue.reddit.redditClone.model.Topic;
import io.mountblue.reddit.redditClone.model.User;
import io.mountblue.reddit.redditClone.service.RoleService;
import io.mountblue.reddit.redditClone.service.TopicService;
import io.mountblue.reddit.redditClone.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class UserRegistrationController {

    private final UserService userService;
    private final TopicService topicService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


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

        List<Topic> interests = new ArrayList<>();
        if(userDto.getInterestIds()!=null){
             interests= userDto.getInterestIds().stream()
                    .map(topicService::findById)
                    .toList();
        }

        Random random = new Random();
        int randomNumber = random.nextInt(29) + 1;
        userDto.setInterests(interests);
        User user = userService.mapDtoToEntity(userDto);
        Role role = roleService.findRoleById(1L);
        System.out.println(role);
        List<User> users = new ArrayList<>();
        users.add(user);
        System.out.println(users);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        System.out.println(roles);
        user.setRoles(roles);
        roleService.save(role);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setProfilePic("beanhead-"+randomNumber+".svg");
        userService.createUser(user);
        return "redirect:/login-page";
    }
}
