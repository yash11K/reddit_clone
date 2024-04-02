package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.model.Topic;
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

    @Override
    public Topic saveTopic(String topicName) {

        Topic existingTopic = topicRepository.findByName(topicName);
        if (existingTopic != null) {
            return existingTopic;
        }
        Topic newTopic = new Topic();
        newTopic.setName(topicName);
        return topicRepository.save(newTopic);
    }
    @Override
    public Topic findByName(String name) {
        return topicRepository.findByName(name);
    }

}
