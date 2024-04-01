package io.mountblue.reddit.redditClone.controller;

import io.mountblue.reddit.redditClone.dto.FullPostViewDto;
import io.mountblue.reddit.redditClone.model.Post;
import io.mountblue.reddit.redditClone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/r")
public class FullPostViewController {
    private final PostService postService;

    @GetMapping("/{subRedditName}/comments/{postId}")
    public String viewComments(
            @PathVariable Long postId,
            Model model
    ) {
        Post post = postService.fetchPostById(postId);
        FullPostViewDto fullPostViewDto = postService.postToFullViewPostDto(post);
        model.addAttribute("fullPostViewDto", fullPostViewDto);
        return "view-comments";
    }

}