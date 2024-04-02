package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.model.Topic;

public interface TopicService {
    Topic saveTopic(String topicName);
    Topic findByName(String name);

}
