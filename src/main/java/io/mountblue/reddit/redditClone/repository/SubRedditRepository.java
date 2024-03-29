package io.mountblue.reddit.redditClone.repository;

import io.mountblue.reddit.redditClone.model.SubReddit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubRedditRepository extends JpaRepository<SubReddit, Long> {
}
