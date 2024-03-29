package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.dto.PostDto;
import io.mountblue.reddit.redditClone.exception.PostNotFound;
import io.mountblue.reddit.redditClone.model.Post;
import io.mountblue.reddit.redditClone.model.SubReddit;
import io.mountblue.reddit.redditClone.model.Topic;
import io.mountblue.reddit.redditClone.repository.PostRepository;
import io.mountblue.reddit.redditClone.repository.SubRedditRepository;
import io.mountblue.reddit.redditClone.exception.SubRedditNotFound;
import io.mountblue.reddit.redditClone.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostManager implements PostService{
    private final PostRepository postRepository;
    private final SubRedditRepository subRedditRepository;
    private final TopicRepository topicRepository;

    @Autowired
    public PostManager(PostRepository postRepository, SubRedditRepository subRedditRepository, TopicRepository topicRepository) {
        this.postRepository = postRepository;
        this.subRedditRepository = subRedditRepository;
        this.topicRepository = topicRepository;
    }

    @Override
    public Post savePostFromDto(PostDto postDto) throws SubRedditNotFound{
        SubReddit subReddit = subRedditRepository.findSubRedditBySubRedditName(postDto.getSubRedditName())
                .orElseThrow(() -> new SubRedditNotFound("Subreddit not found with name: " + postDto.getSubRedditName()));
        List<Topic> topics = topicRepository.findByNameInOrderByName(postDto.getTopics());
        return postRepository.save(Post.builder()
                .subreddit(subReddit)
                .title(postDto.getTitle())
                .body(postDto.getBody())
//                .mediaUri()
//                .flairs(subReddit.getFlairs())
                .isCommentsMediaAllowed(postDto.isCommentMediaAllowed())
                .isPublished(postDto.isPublished())
                .popularityScore((double) 0)
                .createdAt(LocalDateTime.now())
                .topics(topics)
//                .opUser()
                .build());
    }

    @Override
    public Post fetchPostById(Long postId) throws PostNotFound{
        return postRepository.findPostByPostId(postId)
                .orElseThrow(()->new PostNotFound("Post not found with id: " + postId));
    }

    @Override
    public Post updatePostFromDto(PostDto postDto, Long postId){
        Post post = postRepository.findPostByPostId(postId)
                .orElseThrow(()-> new PostNotFound("Post not found with id: " + postId));
        post.setUpdatedAt(LocalDateTime.now());

        List<Topic> topics = topicRepository.findByNameInOrderByName(postDto.getTopics());
        post.setBody(postDto.getBody());
        post.setTitle(postDto.getTitle());
//        post.setMediaUri();
        post.setTopics(topics);
        post.setPublished(postDto.isPublished());
        post.setCommentsMediaAllowed(postDto.isCommentMediaAllowed());
        postRepository.save(post);
        return post;
    }

    @Override
    public String deletePostById(Long postId){
        postRepository.delete(postRepository.findPostByPostId(postId)
                .orElseThrow(()-> new PostNotFound("Post not found with id: " + postId)));
        return "delete post operation successful";
    }
}