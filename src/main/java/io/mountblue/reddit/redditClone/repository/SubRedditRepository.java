package io.mountblue.reddit.redditClone.repository;

import io.mountblue.reddit.redditClone.model.Flair;
import io.mountblue.reddit.redditClone.model.SubReddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubRedditRepository extends JpaRepository<SubReddit, Long> {
    Optional<SubReddit> findSubRedditBySubRedditName(String name);
}
