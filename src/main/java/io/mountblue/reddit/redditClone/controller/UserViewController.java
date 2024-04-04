package io.mountblue.reddit.redditClone.controller;

import io.mountblue.reddit.redditClone.dto.UserDto;
import io.mountblue.reddit.redditClone.model.Comment;
import io.mountblue.reddit.redditClone.model.Post;
import io.mountblue.reddit.redditClone.model.User;
import io.mountblue.reddit.redditClone.repository.RoleRepository;
import io.mountblue.reddit.redditClone.repository.UserRepository;
import io.mountblue.reddit.redditClone.service.CommentService;
import io.mountblue.reddit.redditClone.service.PostService;
import io.mountblue.reddit.redditClone.service.SubRedditService;
import io.mountblue.reddit.redditClone.service.UserService;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class UserViewController {
    private final UserService userService;
    private final PostService postService;
    private final SubRedditService subRedditService;
    private final CommentService commentService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @GetMapping("/u/{username}")
    public String getUserView(@PathVariable String username, Model model, Principal principal) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return "error";
        }
        List<Post> posts = postService.getAllPostsByUser(username);
        List<Comment> comments = commentService.getAllCommentsByUser(username);


        List<Object> postsAndComments = new ArrayList<>();
        postsAndComments.addAll(posts);
        postsAndComments.addAll(comments);


        postsAndComments.sort((a, b) -> {
            LocalDateTime timeA = (a instanceof Post) ? ((Post) a).getCreatedAt() : ((Comment) a).getCreatedAt();
            LocalDateTime timeB = (b instanceof Post) ? ((Post) b).getCreatedAt() : ((Comment) b).getCreatedAt();
            return timeB.compareTo(timeA);
        });
        UserDto userDto = UserDto.builder().username(user.getUsername()).mediaUri(user.getProfilePic()).build();
        model.addAttribute("userDto", userDto);
        model.addAttribute("user", user);
        model.addAttribute("posts", postService.getAllPostsByUser(username));
        model.addAttribute("comments", commentService.getAllCommentsByUser(username));
        model.addAttribute("postsAndComments", postsAndComments);
        return "user-view";

    }
    @GetMapping("/u/{userId}/edit")
    public String showEditProfilePage(@PathVariable Long userId, Model model, Principal principal) {

        String loggedInUsername = userService.findByUsername(principal.getName()).getUsername() ;
        if (!userService.getUserById(userId).getUsername().equals(loggedInUsername)) {
            return "access-denied";
        }
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "user-profile-edit";
    }

    @PostMapping("/u/{userId}/edit")
    public String editProfile(@PathVariable Long userId, @ModelAttribute User user, Principal principal) {
        User updateUser = userService.getUserById(userId);
        updateUser.setDisplayName(user.getDisplayName());
        updateUser.setBio(user.getBio());
        userRepository.save(updateUser);
        return "redirect:/u/"+ updateUser.getUsername();
    }

    @PostMapping("/u/{userId}/delete")
    public String deleteUser(@PathVariable Long userId) {
        userService.getUserById(userId).getRoles().clear();
        userService.deleteUser(userId);
        return "redirect:/feed/all";
    }
}
