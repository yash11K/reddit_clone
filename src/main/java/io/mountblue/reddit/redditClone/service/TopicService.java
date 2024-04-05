package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.model.Topic;

import java.util.List;
import java.util.Optional;

public interface TopicService {
    Topic saveTopic(String topicName);
    Optional<Topic> findByName(String name);
    List<Topic> getAllTopics();
    Topic findById(Long id);

    List<Topic> fetchAllTopics();

    List<Topic> fetchSelectedTopic(List<String> topicNames);
}
