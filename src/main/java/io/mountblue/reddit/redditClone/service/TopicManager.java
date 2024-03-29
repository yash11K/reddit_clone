package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicManager implements TopicService{
    TopicRepository topicRepository;

    @Autowired
    public TopicManager(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

}
