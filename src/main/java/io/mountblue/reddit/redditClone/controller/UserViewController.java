package io.mountblue.reddit.redditClone.controller;

import io.mountblue.reddit.redditClone.model.Comment;
import io.mountblue.reddit.redditClone.model.Post;
import io.mountblue.reddit.redditClone.model.User;
import io.mountblue.reddit.redditClone.service.CommentService;
import io.mountblue.reddit.redditClone.service.PostService;
import io.mountblue.reddit.redditClone.service.SubRedditService;
import io.mountblue.reddit.redditClone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserViewController {
    private final UserService userService;
    private final PostService postService;
    private final SubRedditService subRedditService;
    private final CommentService commentService;

    @Autowired
    public UserViewController(UserService userService, PostService postService, SubRedditService subRedditService, CommentService commentService) {
        this.userService = userService;
        this.postService = postService;
        this.subRedditService = subRedditService;
        this.commentService=commentService;
    }

    @GetMapping("/u/{username}")
    public String getUserView(@PathVariable String username, Model model) {
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

        model.addAttribute("user", user);
        model.addAttribute("posts", postService.getAllPostsByUser(username));
        model.addAttribute("comments", commentService.getAllCommentsByUser(username));
        model.addAttribute("postsAndComments", postsAndComments);
        return "user-view";

    }
    @GetMapping("/u/{userId}/edit")
    public String showEditProfilePage(@PathVariable Long userId, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "user-profile-edit";
    }

    @PostMapping("/u/{userId}/edit")
    public String editProfile(@PathVariable Long userId, @ModelAttribute User user) {
        User user1=userService.findByUsername("October22");
        userService.updateUser(user);
        System.out.println(user.getUsername());
        return "redirect:/u/" + user1.getUsername(); //slight error check
    }

    @PostMapping("/u/{userId}/delete")
    public String deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return "user-registration";  //redirect to home
    }
}
