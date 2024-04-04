package io.mountblue.reddit.redditClone.mapper;

import io.mountblue.reddit.redditClone.dto.PostDto;
import io.mountblue.reddit.redditClone.exception.SubRedditNotFound;
import io.mountblue.reddit.redditClone.model.Post;
import io.mountblue.reddit.redditClone.service.FlairService;
import io.mountblue.reddit.redditClone.service.SubRedditService;
import io.mountblue.reddit.redditClone.service.TopicService;
import io.mountblue.reddit.redditClone.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class PostMapper {
    private SubRedditService subRedditService;
    private TopicService topicService;
    private FlairService flairService;
    private UserService userService;

    public Post newPostMapper(PostDto postDto, String mediaUri){
        return Post.builder()
                .subReddit(subRedditService.findSubRedditByName(
                        postDto.getSubRedditName())
                        .orElseThrow(()-> new SubRedditNotFound("No subReddit with name : " + postDto.getSubRedditName())))
                .title(postDto.getTitle())
                .body(postDto.getBody())
                .topics(topicService.fetchSelectedTopic(postDto.getTopics()))
                .flairs(flairService.fetchFlairByFlairName(postDto.getFlairs()))
                .isPublished(postDto.isPublished())
                .isCommentsMediaAllowed(postDto.isCommentMediaAllowed())
                .createdAt(LocalDateTime.now())
                .opUser(postDto.getOp())
                .voteCount(0L)
                .popularityScore(0.0)
                .mediaUri(mediaUri)
                .build();
    }
}
