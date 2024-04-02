package io.mountblue.reddit.redditClone.controller;

import io.mountblue.reddit.redditClone.dto.FullPostViewDto;
import io.mountblue.reddit.redditClone.model.Post;
import io.mountblue.reddit.redditClone.service.PostService;
import io.mountblue.reddit.redditClone.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class VoteController {
    private PostService postService;
    private VoteService voteService;
    @PostMapping("/voting")
    public String voting(
            @RequestParam(name = "postId") Long postId,
            @RequestParam(name = "votetype") String voteType,
            Model model
    ) {
        Post post = postService.fetchPostById(postId);
        voteService.votes(post, voteType);
        FullPostViewDto fullPostViewDto = postService.postToFullViewPostDto(post);
        String subRedditName = post.getSubReddit().getSubRedditName();
        Long voteCounts = post.getVoteCount();
        model.addAttribute("fullPostViewDto", fullPostViewDto);
        model.addAttribute("votesCount", voteCounts);
        return "redirect:/r/" + subRedditName + "/comments/" + postId;
    }

}
