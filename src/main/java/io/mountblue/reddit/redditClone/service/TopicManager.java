package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.model.Topic;
import io.mountblue.reddit.redditClone.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicManager implements TopicService{
    TopicRepository topicRepository;

    @Autowired
    public TopicManager(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public Topic saveTopic(String topicName) {

        Optional<Topic> existingTopic = topicRepository.findByName(topicName);
        if (existingTopic.isPresent()) {
            return existingTopic.get();
        }
        Topic newTopic = new Topic();
        newTopic.setName(topicName);
        return topicRepository.save(newTopic);
    }
    @Override
    public Optional<Topic> findByName(String name) {
        return topicRepository.findByName(name);
    }

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }
    @Override
    public Topic findById(Long id) {
        return topicRepository.findById(id).orElse(null);
    }
    @Override
    public List<Topic> fetchAllTopics(){
        return topicRepository.findAll();
    }

    @Override
    public List<Topic> fetchSelectedTopic(List<String> topicNames){
        return topicRepository.findByNameInOrderByName(topicNames);
    }
}
