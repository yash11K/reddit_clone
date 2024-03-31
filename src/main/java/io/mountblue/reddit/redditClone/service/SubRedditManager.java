package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.dto.*;
import io.mountblue.reddit.redditClone.model.Post;
import io.mountblue.reddit.redditClone.repository.UserRepository;
import io.mountblue.reddit.redditClone.exception.FlairNotFound;
import io.mountblue.reddit.redditClone.exception.RuleNotFound;
import io.mountblue.reddit.redditClone.exception.SubRedditNotFound;
import io.mountblue.reddit.redditClone.model.Flair;
import io.mountblue.reddit.redditClone.model.Rule;
import io.mountblue.reddit.redditClone.model.SubReddit;
import io.mountblue.reddit.redditClone.repository.FlairRepository;
import io.mountblue.reddit.redditClone.repository.RuleRepository;
import io.mountblue.reddit.redditClone.repository.SubRedditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class SubRedditManager implements SubRedditService{

    private final SubRedditRepository subRedditRepository;
    private final FlairRepository flairRepository;
    private final RuleRepository ruleRepository;
    @Override
    public SubRedditDto save(SubRedditDto subRedditDto, Principal principal) {
        subRedditRepository.save(
                SubReddit.builder()
                        .subRedditName(subRedditDto.getSubRedditName())
                        .createdAt(LocalDateTime.now())
//                        .modUser(userRepository.findByUsername(principal.getName()))
                        .build()
        );
        String subRedditName = subRedditDto.getSubRedditName();
        subRedditDto.setSubRedditName(subRedditNameFormatter(subRedditName));
        return subRedditDto;
    }

    @Override
    public SubReddit update(SubRedditDto subRedditDto, String subRedditName) {
        SubReddit subReddit = subRedditRepository.findSubRedditBySubRedditName(subRedditName)
                .orElseThrow(()->new SubRedditNotFound("SubReddit not found with name: " + subRedditName));
        subReddit.setUpdatedAt(LocalDateTime.now());
        subReddit.setDescription(subRedditDto.getSubRedditDescription());
        subRedditRepository.save(subReddit); //FIRST SAVE THE FLAIR AND RULE
        return subReddit;
    }

    @Override
    public SubReddit show(String subRedditName) {
        return subRedditRepository.findSubRedditBySubRedditName(subRedditName)
                .orElseThrow(()->new SubRedditNotFound("SubReddit not found with name: " + subRedditName));

    }

    @Override
    public void delete(Long subRedditId) {
        subRedditRepository.deleteById(subRedditId);
    }

    public static String subRedditNameFormatter(String subRedditName){
        subRedditName = "r/" + subRedditName;
        return subRedditName;
    }

    @Override
    public SubReddit saveRule(RuleDto ruleDto, String subRedditName){
        SubReddit subReddit = subRedditRepository.findSubRedditBySubRedditName(subRedditName)
                .orElseThrow(()->new SubRedditNotFound("SubReddit not found with name: " + subRedditName));

        subReddit.getRules().add(ruleRepository.save(
                    Rule.builder()
                    .subReddit(subReddit)
                    .rule(ruleDto.getRule())
                    .created_at(LocalDateTime.now())
                    .build()
                ));
        subRedditRepository.save(subReddit);
        return subReddit;
    }

    @Override
    public SubReddit saveFlair(FlairDto flairDto, String subRedditName){
        SubReddit subReddit = subRedditRepository.findSubRedditBySubRedditName(subRedditName)
                .orElseThrow(()->new SubRedditNotFound("SubReddit not found with name: " + subRedditName));

        subReddit.getFlairs().add(flairRepository.save(
                Flair.builder()
                        .flairName(flairDto.getName())
                        .subReddit(subReddit)
                        .color(flairDto.getColor())
                        .build()
        ));
        subRedditRepository.save(subReddit);
        return subReddit;
    }

    @Override
    public SubReddit updateRule(String subRedditName, RuleDto ruleDto, Long ruleId) {
        SubReddit subReddit = subRedditRepository.findSubRedditBySubRedditName(subRedditName)
                .orElseThrow(()->new SubRedditNotFound("SubReddit not found with name: " + subRedditName));
        Rule rule = ruleRepository.findById(ruleId).orElseThrow(() ->new RuleNotFound("Rule Not Found"));
        ruleRepository.save(Rule.builder()
                .ruleId(rule.getRuleId())
                .rule(ruleDto.getRule())
                .subReddit(rule.getSubReddit())
                .created_at(rule.getCreated_at())
                .build()
        );
        return subReddit;
    }

    @Override
    public String deleteRule(Long ruleId) {
        Rule rule = ruleRepository.findById(ruleId).orElseThrow(()->new RuleNotFound("Rule Not Found"));
        ruleRepository.delete(rule);
        return "Successfully Deleted";
    }

    @Override
    public String deleteFlair(Long flairId) {
        Flair flair = flairRepository.findById(flairId).orElseThrow(()->new FlairNotFound("Flair Not Found"));
        flairRepository.delete(flair);
        return "Successfully Deleted";
    }

    @Override
    public SubRedditViewDto subRedditToSubRedditViewDto(SubReddit subReddit) {
        List<Rule> rule = subReddit.getRules();
        List<String> rules = new ArrayList<>();
        for(Rule stringRule : rule) {
            rules.add(stringRule.getRule());
        }
        List<Post> subRedditPosts = subReddit.getPosts();
        List<SubRedditPostDto> subRedditPostDtos = new ArrayList<>();
        for(Post post : subRedditPosts) {
            Long votes = (long) post.getVotes().size();
            Long comments = (long) post.getComments().size();
            String createdAt = calculateTimeAgo(post.getCreatedAt());
            subRedditPostDtos.add(SubRedditPostDto.builder()
                    .body(post.getBody())
                    .title(post.getTitle())
                    .opUser(post.getOpUser())
                    .postId(post.getPostId())
                    .voteCount(votes)
                    .commentCount(comments)
                    .createdAt(createdAt)
                    .build());

        }
        String avatar = subReddit.getAvatar();
        String banner = subReddit.getBanner();
        return SubRedditViewDto.builder()
                    .subRedditId(subReddit.getSubRedditId())
                    .subRedditDescription(subReddit.getDescription())
                    .subRedditName(subReddit.getSubRedditName())
                    .rules(rules)
                    .subRedditPostDtos(subRedditPostDtos)
                    .build();
    }


    public static String calculateTimeAgo(LocalDateTime creationDateTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(creationDateTime, now);
        long hours = duration.toHours();
        long days = duration.toDays();
        if (hours < 24) {
            return hours + " hours ago";
        } else {
            return days + " days ago";
        }
    }
}
