package io.mountblue.reddit.redditClone.controller;

import io.mountblue.reddit.redditClone.dto.VoteRequest;
import io.mountblue.reddit.redditClone.dto.VoteResponse;
import io.mountblue.reddit.redditClone.mapper.PostMapper;
import io.mountblue.reddit.redditClone.model.Post;
import io.mountblue.reddit.redditClone.model.User;
import io.mountblue.reddit.redditClone.model.Vote;
import io.mountblue.reddit.redditClone.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class VoteController {
    PostService postService;
    TopicService topicService;
    SubRedditService subRedditService;
    FlairService flairService;
    PostMapper postMapper;
    UserService userService;
    VoteService voteService;

    @GetMapping("/posts/{postId}/vote")
    public ResponseEntity<VoteResponse> voteOnPost(@PathVariable Long postId, @RequestBody VoteRequest voteRequest, Principal principal) {
        User user = userService.getUserById(22L);
        Post post =  postService.fetchPostById((postId));

        Optional<Vote> existingVote =  voteService.findVoteExist(post, user);

        if (existingVote.isPresent()) {
            Vote vote = existingVote.get();
            if (vote.getVoteType() == voteRequest.getVoteType()) {
                voteService.delete(vote);
                post.setVoteCount(post.getVoteCount() - voteRequest.getVoteType().getDirection());
            } else {
                vote.setVoteType(voteRequest.getVoteType());
                post.setVoteCount(post.getVoteCount() + (2 * voteRequest.getVoteType().getDirection()));
            }
        } else {
            Vote vote = new Vote();
            vote.setPost(post);
            vote.setUser(user);
            vote.setVoteType(voteRequest.getVoteType());
            voteService.save(vote);
            post.setPopularityScore(post.getPopularityScore() + voteRequest.getVoteType().getDirection());
        }

        postService.save(post);

        VoteResponse voteResponse = new VoteResponse();
        voteResponse.setVoteCount(Math.toIntExact(post.getVoteCount()));
        voteResponse.setUserVote(existingVote.map(Vote::getVoteType).orElse(null));

        return ResponseEntity.ok(voteResponse);
    }

}
