package io.mountblue.reddit.redditClone.repository;

import io.mountblue.reddit.redditClone.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Optional<Topic> findByName(String name);
    List<Topic> findByNameInOrderByName(List<String> topicNames);
}
