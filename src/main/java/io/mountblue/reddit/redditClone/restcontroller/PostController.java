package io.mountblue.reddit.redditClone.restcontroller;

import io.mountblue.reddit.redditClone.dto.FlairDto;
import io.mountblue.reddit.redditClone.dto.PostDto;
import io.mountblue.reddit.redditClone.model.Post;
//import io.mountblue.reddit.redditClone.service.MediaService;
import io.mountblue.reddit.redditClone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {
    PostService postService;
//    MediaService mediaService;

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

    @PostMapping("/{postId}/flair/new")
    public Post saveFlair(@PathVariable Long postId,@RequestBody FlairDto flairDto){
        return postService.saveFlairToPostFromDto(flairDto, postId);
    }
}
