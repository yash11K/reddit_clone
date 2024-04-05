package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.model.Role;
import io.mountblue.reddit.redditClone.repository.RoleRepository;
import io.mountblue.reddit.redditClone.restcontroller.TopicController;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@AllArgsConstructor
public class Startup implements CommandLineRunner {
    private RoleRepository roleRepository;
    private TopicController topicController;

    private static final Logger logger = LoggerFactory.getLogger(Startup.class);

    @Override
    public void run(String...args) throws Exception {
        roleRepository.save(Role.builder().id(1L).name("ROLE_REDDITOR").build());
        topicController.saveTopicsFromProperties();
        logger.info("Application started with command-line arguments: {} . \n To kill this application, press Ctrl + C.", Arrays.toString(args));
    }
}
