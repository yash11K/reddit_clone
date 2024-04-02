package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.dto.FlairDto;
import io.mountblue.reddit.redditClone.dto.FullPostViewDto;
import io.mountblue.reddit.redditClone.dto.PostDto;
import io.mountblue.reddit.redditClone.exception.FlairNotFound;
import io.mountblue.reddit.redditClone.exception.PostNotFound;
import io.mountblue.reddit.redditClone.model.Post;
import io.mountblue.reddit.redditClone.model.SubReddit;
import io.mountblue.reddit.redditClone.model.Topic;
import io.mountblue.reddit.redditClone.model.*;
import io.mountblue.reddit.redditClone.repository.FlairRepository;
import io.mountblue.reddit.redditClone.repository.PostRepository;
import io.mountblue.reddit.redditClone.repository.SubRedditRepository;
import io.mountblue.reddit.redditClone.exception.SubRedditNotFound;
import io.mountblue.reddit.redditClone.repository.TopicRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PostManager implements PostService{
    private final PostRepository postRepository;
    private final SubRedditRepository subRedditRepository;
    private final TopicRepository topicRepository;
    private final FlairRepository flairRepository;

    @Override
    public Post savePostFromDto(PostDto postDto) throws SubRedditNotFound{
        SubReddit subReddit = subRedditRepository.findSubRedditBySubRedditName(postDto.getSubRedditName())
                .orElseThrow(() -> new SubRedditNotFound("Subreddit not found with name: " + postDto.getSubRedditName()));

        List<Topic> topics = topicRepository.findByNameInOrderByName(postDto.getTopics());
        return postRepository.save(Post.builder()
                .subReddit(subReddit)
                .title(postDto.getTitle())
                .body(postDto.getBody())
//                .mediaUri()
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

    @Override
    public Post saveFlairToPostFromDto(FlairDto flairDto, Long postId){
        Post post = postRepository.findPostByPostId(postId)
                .orElseThrow(()-> new PostNotFound("no post with id : " + postId));
        post.getFlairs().add(flairRepository.findFlairByFlairName(flairDto.getName())
                .orElseThrow(()->new FlairNotFound("no flair with name: " + flairDto.getName())));
        return postRepository.save(post);
    }
    @Override
    public List<Post> getAllPostsByUser(String username) {
        return postRepository.findAllByOpUserUsername(username);
    }


    @Override
    public Post save(Post post){
        return postRepository.save(post);
    }

    @Override
    public List<Post> fetchAllPostByPublished(boolean isPublished){
        return postRepository.findPostsByIsPublished(isPublished);
    }

    public FullPostViewDto postToFullViewPostDto(Post post) {
        return FullPostViewDto.builder()
                .postId(post.getPostId())
                .subReddit(post.getSubReddit())
                .createdAt(post.getCreatedAt())
                .opUser(post.getOpUser())
                .title(post.getTitle())
                .flairs(post.getFlairs())
                .voteCount(post.getVoteCount())
                .body(post.getBody())
                .post(post)
                .CommentCount((long) post.getComments().size())
//                .mediaUri(post.getMediaUri())
                .comments(post.getComments())
                .build();
    }

    @Override
    public List<Comment> findAllCommentsByPostId(Long postId) {
        return postRepository.findAllCommentsByPostId(postId);
    }
}
