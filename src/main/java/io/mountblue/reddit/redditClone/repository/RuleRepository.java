package io.mountblue.reddit.redditClone.repository;

import io.mountblue.reddit.redditClone.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
}
