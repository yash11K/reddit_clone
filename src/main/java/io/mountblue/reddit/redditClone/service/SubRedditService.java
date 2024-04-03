package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.dto.FlairDto;
import io.mountblue.reddit.redditClone.dto.RuleDto;
import io.mountblue.reddit.redditClone.dto.SubRedditDto;
import io.mountblue.reddit.redditClone.dto.SubRedditViewDto;
import io.mountblue.reddit.redditClone.model.SubReddit;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface SubRedditService {
    SubRedditDto save(SubRedditDto subRedditDto, Principal principal);

    SubReddit update(SubRedditDto subRedditDto, String subRedditName);

    SubReddit show(String subRedditName);

    void delete(Long subRedditId);

    SubReddit saveRule(RuleDto ruleDto, String subRedditName);

    SubReddit saveFlair(FlairDto flairDto, String subRedditName);

    SubReddit updateRule(String subRedditName, RuleDto ruleDto, Long ruleId);

    String deleteRule(Long ruleId);

    String deleteFlair(Long flairId);

    SubRedditViewDto subRedditToSubRedditViewDto(SubReddit subReddit);

    List<String> fetchAllSubRedditNames();

    Optional<SubReddit> findSubRedditByName(String subRedditName);

    List<SubReddit> findSubRedditsByMod(String opUsername);

    List<String> previousSubRedditNames();

    Long ruleId(SubReddit subReddit, String rule);

    void saveDirectSubReddit(SubReddit subReddit);
}
