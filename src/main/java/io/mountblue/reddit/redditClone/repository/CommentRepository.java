package io.mountblue.reddit.redditClone.repository;

import io.mountblue.reddit.redditClone.model.Comment;
import io.mountblue.reddit.redditClone.model.Post;
import io.mountblue.reddit.redditClone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByUserUsername(String username);

    List<Comment> findCommentByPost(Post post);
    int countCommentByPost(Post post);

    int countByUser(User user);
}