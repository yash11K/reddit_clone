package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.dto.CommentDto;
import io.mountblue.reddit.redditClone.model.Comment;
import io.mountblue.reddit.redditClone.model.Post;
import io.mountblue.reddit.redditClone.model.User;

import java.util.List;

public interface CommentService {
    Comment save(CommentDto commentDto, Long postId);

    Comment updateComment(Long commentId, CommentDto commentDto);

    List<Comment> findAll();

    Comment getCommentById(Long commentId);

    void delete(Long commentId);
    List<Comment> getAllCommentsByUser(String username);

    int getCommentCount(Post post);
    void updateCommentByComment(Comment comment, String updatedComment);

    int getUserCommentCount(User user);
}