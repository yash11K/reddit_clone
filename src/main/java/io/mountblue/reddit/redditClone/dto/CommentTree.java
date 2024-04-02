package io.mountblue.reddit.redditClone.dto;

import io.mountblue.reddit.redditClone.model.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentTree {
    private Map<Comment, List<Comment>> parentChildMap;


    private CommentTree() {
        parentChildMap = new HashMap<>();
    }

    public void addComment(Comment comment) {
        Comment parentComment = comment.getParentComment();
        parentChildMap.putIfAbsent(comment, new ArrayList<>());
        if (parentComment != null) {
            parentChildMap.get(parentComment).add(comment);
        }
    }
}