package io.mountblue.reddit.redditClone.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
@Setter
public class CommentTree {
    private Map<Comment, List<Comment>> parentChildMap;

    private CommentTree() {
        parentChildMap = new HashMap<>();
    }

    public void addComment(Comment comment) {
        Comment parentComment = comment.getParentComment();
        if(parentComment != null) {
            parentChildMap.putIfAbsent(parentComment, new ArrayList<>());
            parentChildMap.get(parentComment).add(comment);
        }
        else {
            parentChildMap.putIfAbsent(null, new ArrayList<>());
            parentChildMap.get(null).add(comment);
        }
    }

//    public Comment fetchComment(Comment comment) {
//        Comment tempComment;
//        if(parentChildMap.containsKey(comment)) {
//             parentChildMap.get(comment).forEach(
//                    childComment -> {
//                        tempComment = fetchComment(childComment);
//                    }
//            );
//        }
//        return comment;
//    }

}