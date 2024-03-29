package io.mountblue.reddit.redditClone.repository;

import io.mountblue.reddit.redditClone.model.SubReddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubRedditRepository extends JpaRepository<SubReddit, Long> {
    Optional<SubReddit> findBySubRedditName(String subRedditName);
}
