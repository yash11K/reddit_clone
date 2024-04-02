package io.mountblue.reddit.redditClone.controller;

import io.mountblue.reddit.redditClone.dto.PostCardDto;
import io.mountblue.reddit.redditClone.dto.VoteRequest;
import io.mountblue.reddit.redditClone.dto.VoteResponse;
import io.mountblue.reddit.redditClone.model.Post;
import io.mountblue.reddit.redditClone.model.User;
import io.mountblue.reddit.redditClone.model.Vote;
import io.mountblue.reddit.redditClone.service.*;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/feed")
public class FeedViewController {
    PostService postService;
    CommentService commentService;
    FlairService flairService;
    SubRedditService subRedditService;
    UserService userService;
    VoteService voteService;
    MediaService mediaService;

    @GetMapping("/all")
    public String viewAllPostFeed(Model model){
        List<PostCardDto> postCardDtos =  postService.fetchAllPostByPublished(true).
                stream().map(
                        post ->
                        PostCardDto.builder()
                                .postId(post.getPostId())
                                .subRedditName(post.getSubReddit().getSubRedditName())
                                .title(post.getTitle())
                                .body(post.getBody())
                                .flairs(post.getFlairs())
                                .media("/feed/image/" + post.getMediaUri())
                                .commentCount(commentService.getCommentCount(post))
//                                .voteCount()
                                .createdAt(SubRedditManager.calculateTimeAgo(post.getCreatedAt()))
                                .build()
                ).toList();
        model.addAttribute("postCards", postCardDtos);
        model.addAttribute("subReddits", subRedditService.fetchAllSubRedditNames());
        model.addAttribute("modSubReddits", subRedditService.findSubRedditsByMod("October22"));
        return "feed/feed-all";
    }

    @GetMapping("/image/{filepath}")
    public ResponseEntity<Resource> getImage(@PathVariable String filepath) throws IOException {
        Resource resource = mediaService.fetchMediaFromUri(filepath);;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .body(resource);
    }

}
