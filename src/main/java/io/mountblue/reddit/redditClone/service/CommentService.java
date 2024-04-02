package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.dto.CommentDto;
import io.mountblue.reddit.redditClone.model.Comment;
import io.mountblue.reddit.redditClone.model.Post;

import java.util.List;

public interface CommentService {
    Comment save(CommentDto commentDto, Long postId);

    Comment updateComment(Long commentId, CommentDto commentDto);

    List<Comment> findAll();

    Comment getCommentById(Long commentId);

    void delete(Long commentId);

    int getCommentCount(Post post);
}