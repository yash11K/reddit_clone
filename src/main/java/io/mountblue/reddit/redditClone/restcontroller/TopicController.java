package io.mountblue.reddit.redditClone.restcontroller;

import io.mountblue.reddit.redditClone.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    @Value("${reddit.topics}")
    private List<String> topicsList;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/save")
    public String saveTopicsFromProperties() {
        for (String topic : topicsList) {
            topicService.saveTopic(topic);
        }
        return "Topics saved successfully";
    }
}
