package io.mountblue.reddit.redditClone.repository;

import io.mountblue.reddit.redditClone.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}