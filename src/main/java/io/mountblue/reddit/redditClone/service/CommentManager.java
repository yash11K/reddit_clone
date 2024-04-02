package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.dto.CommentDto;
import io.mountblue.reddit.redditClone.exception.CommentNotFound;
import io.mountblue.reddit.redditClone.exception.PostNotFound;
import io.mountblue.reddit.redditClone.model.Comment;
import io.mountblue.reddit.redditClone.model.Post;
import io.mountblue.reddit.redditClone.repository.CommentRepository;
import io.mountblue.reddit.redditClone.repository.PostRepository;
import io.mountblue.reddit.redditClone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFound("no comment for id : " + commentId));

        existingComment.setUpdatedAt(LocalDateTime.now());
        existingComment.setComment(commentDto.getText());
        existingComment.setPost(postRepository.findPostByPostId(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFound("Post not found: ")));
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
        return commentRepository.findById(commentId)
                .orElseThrow(()-> new CommentNotFound("Comment not found for id: " + commentId));
    }

    @Override
    public void delete(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    private Comment convertDtoToModel(CommentDto commentDto, Long postId) {
        Post post = postRepository.findPostByPostId(postId)
                .orElseThrow(() -> new PostNotFound("Post not found for id : " + postId));

        String commentBody = commentDto.getText();
        Comment parentComment = null;
        if(commentDto.getParentCommentId()!=null){
            parentComment = commentRepository.findById(commentDto.getParentCommentId().get()).orElseThrow(
                    () -> new CommentNotFound("Comment not found for parent id: " + commentDto.getParentCommentId().get())
            );
        }

        return Comment.builder()
                .comment(commentBody)
                .createdAt(LocalDateTime.now())
                .parentComment(parentComment)
                .post(post)
//                .user()
//                .votes()
//                .mediaUri()
                .build();
    }
}