package io.mountblue.reddit.redditClone.restcontroller;

import io.mountblue.reddit.redditClone.repository.TopicRepository;
import io.mountblue.reddit.redditClone.service.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
@RequestMapping("/api/topics")
@AllArgsConstructor
public class TopicController {

    private final TopicService topicService;
    private final TopicRepository topicRepository;

    @Value("${reddit.topics}")
    private List<String> topicsList;

    public void saveTopicsFromProperties() {
        for (String topic : topicsList) {
            if(topicRepository.findByName(topic).isEmpty())
            {
                topicService.saveTopic(topic);
            }
        }
    }
}
