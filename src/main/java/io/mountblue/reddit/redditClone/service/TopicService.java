package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.model.Topic;

import java.util.List;

public interface TopicService {
    Topic saveTopic(String topicName);
    Topic findByName(String name);
    List<Topic> getAllTopics();
    Topic findById(Long id);

}
