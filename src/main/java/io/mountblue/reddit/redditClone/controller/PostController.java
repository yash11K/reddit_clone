package io.mountblue.reddit.redditClone.controller;

import io.mountblue.reddit.redditClone.dto.PostDto;
import io.mountblue.reddit.redditClone.exception.PostNotFound;
import io.mountblue.reddit.redditClone.exception.SubRedditNotFound;
import io.mountblue.reddit.redditClone.model.Post;
import io.mountblue.reddit.redditClone.service.PostService;
import jakarta.persistence.JoinColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/post")
public class PostController {
    PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public Post savePost(@RequestBody PostDto postDto, Principal principal){
        return postService.savePostFromDto(postDto);
    };

    @GetMapping("/{postId}")
    public Post fetchPost(@PathVariable Long postId){
       return postService.fetchPostById(postId);
    }

    @PostMapping("/update/{postId}")
    public Post updatePost(@PathVariable Long postId, @RequestBody PostDto postDto){
        return postService.updatePostFromDto(postDto, postId);
    }

    @PostMapping("/delete/{postId}")
    public String deletePost(@PathVariable Long postId){
        return postService.deletePostById(postId);
    }
}
