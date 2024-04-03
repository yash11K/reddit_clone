package io.mountblue.reddit.redditClone.repository;

import io.mountblue.reddit.redditClone.model.Flair;
import io.mountblue.reddit.redditClone.model.Post;
import io.mountblue.reddit.redditClone.model.SubReddit;
import io.mountblue.reddit.redditClone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface SubRedditRepository extends JpaRepository<SubReddit, Long> {
    Optional<SubReddit> findSubRedditBySubRedditName(String name);
    List<SubReddit> findSubRedditByModUser(User modUser);

    @Query("SELECT s.subRedditName FROM SubReddit s")
    List<String> findSubRedditNames();
}
