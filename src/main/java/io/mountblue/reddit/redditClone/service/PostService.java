package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.dto.PostDto;
import io.mountblue.reddit.redditClone.model.Post;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
    Post savePostFromDto(PostDto postDto);

    Post fetchPostById(Long postId);

    Post updatePostFromDto(PostDto postDto, Long postId);

    String deletePostById(Long postId);
}
