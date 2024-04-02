package io.mountblue.reddit.redditClone.repository;

import io.mountblue.reddit.redditClone.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findPostByPostId(Long postId);
    List<Post> findPostsByIsPublished(boolean isPublished);
}
