package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.dto.CommentDto;
import io.mountblue.reddit.redditClone.exception.PostNotFound;
import io.mountblue.reddit.redditClone.exception.SubRedditNotFound;
import io.mountblue.reddit.redditClone.model.Comment;
import io.mountblue.reddit.redditClone.model.Post;
import io.mountblue.reddit.redditClone.repository.CommentRepository;
import io.mountblue.reddit.redditClone.repository.PostRepository;
import io.mountblue.reddit.redditClone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentManager implements CommentService{
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;

    @Override
    public Comment save(CommentDto commentDto, Long postId) {
        Comment comment = convertDtoToModel(commentDto, postId);
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public Comment updateComment(Long commentId, CommentDto commentDto) {
        Comment existingComment = commentRepository.findById(commentId).get();
        existingComment.setUpdatedAt(LocalDateTime.now());
        existingComment.setComment(commentDto.getText());
        existingComment.setPost(postRepository.findPostByPostId(commentDto.getPostId()).orElseThrow(() -> new PostNotFound("Post not found: ")));
//        existingComment.setMediaUri();
//        existingComment.setUser();
//        existingComment.setVotes();
        commentRepository.save(existingComment);
        return existingComment;
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).get();
    }

    @Override
    public void delete(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    private Comment convertDtoToModel(CommentDto commentDto, Long postId) {
        Post post = postRepository.findPostByPostId(commentDto.getPostId()).orElseThrow(() -> new PostNotFound("Post not found: "));
        String commentText = commentDto.getText();
        Long parentCommentId = commentDto.getParentCommentId().orElse(null);
        Comment parentComment = parentCommentId != null ? commentRepository.findById(parentCommentId).orElse(null) : null;


        return Comment.builder()
                .comment(commentText)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .parentComment(parentComment)
                .post(post)
                .user(null)
                .votes(null)
                .mediaUri(null)
                .build();
    }
//    private String getLoggedInUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication.getName();
//    }
}