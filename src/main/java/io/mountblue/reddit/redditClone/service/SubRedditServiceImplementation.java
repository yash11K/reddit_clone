package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.dto.SubRedditDto;
import io.mountblue.reddit.redditClone.exception.SubRedditNotFound;
import io.mountblue.reddit.redditClone.model.Flair;
import io.mountblue.reddit.redditClone.model.Rule;
import io.mountblue.reddit.redditClone.model.SubReddit;
import io.mountblue.reddit.redditClone.repository.RuleRepository;
import io.mountblue.reddit.redditClone.repository.SubRedditRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class SubRedditServiceImplementation implements SubRedditService{

    private final SubRedditRepository subRedditRepository;
    private final RuleRepository ruleRepository;
    @Override
    public SubRedditDto save(SubRedditDto subRedditDto, Principal principal) {
        subRedditRepository.save(
                SubReddit.builder()
                        .subRedditName(subRedditDto.getSubRedditName())
                        .createdAt(LocalDateTime.now())
                        //.modUser(userRepository.findByUsername(principal.getName()))
                        .build()
        );
        String subRedditName = subRedditDto.getSubRedditName();
        System.out.println(subRedditName);
        subRedditDto.setSubRedditName("r/" + subRedditName);
        return subRedditDto;
    }

    @Override
    public SubRedditDto update(SubRedditDto subRedditDto, String subRedditName) {
        SubReddit subReddit = subRedditRepository.findSubRedditBySubRedditName(subRedditName)
                .orElseThrow(()->new SubRedditNotFound("Subreddit not found with name: " + subRedditName));
        List<String> rules = subRedditDto.getRules();
        List<Rule> subRedditRules = new ArrayList<>();
        for(String rule: rules) {
            Rule subRedditRule = Rule.builder()
                    .rule(rule)
                    .created_at(LocalDateTime.now())
                    .subReddit(subReddit)
                    .build();
            ruleRepository.save(subRedditRule);
            subRedditRules.add(subRedditRule);
        }
        subRedditRepository.save(SubReddit.builder()
                .subRedditId(subReddit.getSubRedditId())
                .subRedditName(subReddit.getSubRedditName())
                .createdAt(subReddit.getCreatedAt())
                .rules(subRedditRules)
                .description(subRedditDto.getSubRedditDescription())
                .build()
        );
        return subRedditDto;
    }

    @Override
    public SubReddit show(String subRedditName) {
        return subRedditRepository.findSubRedditBySubRedditName(subRedditName)
                .orElseThrow(()->new SubRedditNotFound("Subreddit not found with name: " + subRedditName));

    }

    @Override
    public void delete(Long subRedditId) {
        subRedditRepository.deleteById(subRedditId);
    }

}
