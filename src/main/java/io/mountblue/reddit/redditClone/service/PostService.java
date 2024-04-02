package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.dto.FlairDto;
import io.mountblue.reddit.redditClone.dto.PostDto;
import io.mountblue.reddit.redditClone.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    Post savePostFromDto(PostDto postDto);

    Post fetchPostById(Long postId);

    Post updatePostFromDto(PostDto postDto, Long postId);

    String deletePostById(Long postId);

    Post saveFlairToPostFromDto(FlairDto flairDto, Long postId);

    List<Post> getAllPostsByUser(String username);
}
