package io.mountblue.reddit.redditClone.repository;

import io.mountblue.reddit.redditClone.model.Comment;
import io.mountblue.reddit.redditClone.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findPostByPostId(Long postId);

    List<Comment> findAllCommentsByPostId(Long postId);
    List<Post> findAllByOpUserUsername(String username);

}
