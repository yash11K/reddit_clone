package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.dto.*;
import io.mountblue.reddit.redditClone.exception.UserNotFound;
import io.mountblue.reddit.redditClone.model.*;
import io.mountblue.reddit.redditClone.exception.FlairNotFound;
import io.mountblue.reddit.redditClone.exception.RuleNotFound;
import io.mountblue.reddit.redditClone.exception.SubRedditNotFound;
import io.mountblue.reddit.redditClone.repository.FlairRepository;
import io.mountblue.reddit.redditClone.repository.RuleRepository;
import io.mountblue.reddit.redditClone.repository.SubRedditRepository;
import io.mountblue.reddit.redditClone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SubRedditManager implements SubRedditService{

    private final SubRedditRepository subRedditRepository;
    private final FlairRepository flairRepository;
    private final RuleRepository ruleRepository;
    private final UserRepository userRepository;

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

            String paragraph = post.getBody();
            String[] words = paragraph.split("\\s+");
            int maxLength = Math.min(words.length, 30);
            String body = String.join(" ", Arrays.copyOf(words, maxLength));

            Long comments = (long) post.getComments().size();
            String createdAt = calculateTimeAgo(post.getCreatedAt());
            subRedditPostDtos.add(SubRedditPostDto.builder()
                    .body(body)
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
        Long subscriberUsers = (long)subReddit.getSubscribedUsers().size();
        List<SubReddit> allSubReddits = subRedditRepository.findAll();
        List<String> allSubRedditNames = new ArrayList<>();
        for(SubReddit subReddit1 : allSubReddits) {
            allSubRedditNames.add(subReddit1.getSubRedditName());
        }

        List<String> previousSubRedditUsernames = previousSubRedditNames();

        return SubRedditViewDto.builder()
                    .subRedditId(subReddit.getSubRedditId())
                    .subRedditDescription(subReddit.getDescription())
                    .subRedditName(subReddit.getSubRedditName())
                    .rules(rules)
                    .previousSubRedditNames(previousSubRedditUsernames)
                    .subRedditPostDtos(subRedditPostDtos)
                    .subscribedUsers(subscriberUsers)
                    .allSubReddits(allSubRedditNames)
                    .banner(subReddit.getBanner())
                    .avatar(subReddit.getAvatar())
                    .build();
    }

    public List<String> previousSubRedditNames() {
        return subRedditRepository.findSubRedditNames();
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

    @Override
    public List<String> fetchAllSubRedditNames(){
        List<String> allSubRedditNames = new ArrayList<>();
        subRedditRepository.findAll().forEach(
                subReddit -> {
                    allSubRedditNames.add(subReddit.getSubRedditName());
                }
        );
        return allSubRedditNames;
    }

    @Override
    public Optional<SubReddit> findSubRedditByName(String subRedditName){
        return subRedditRepository.findSubRedditBySubRedditName(subRedditName);
    }

    @Override
    public List<SubReddit> findSubRedditsByMod(String opUsername){
        User mod = userRepository.findByUsername(opUsername)
                .orElseThrow(()->new UserNotFound("no username exists as : " + opUsername));

        return subRedditRepository.findSubRedditByModUser(mod);
    }

    public Long ruleId(SubReddit subReddit, String rule) {
        Rule selectedRule = ruleRepository.findRuleBySubRedditAndRule(subReddit, rule);
        return selectedRule.getRuleId();
    }

    @Override
    public void saveDirectSubReddit(SubReddit subReddit){
        subReddit.setUpdatedAt(LocalDateTime.now());
        subRedditRepository.save(subReddit);
    }
}
