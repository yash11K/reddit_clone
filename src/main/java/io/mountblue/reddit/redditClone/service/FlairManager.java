package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.exception.SubRedditNotFound;
import io.mountblue.reddit.redditClone.model.Flair;
import io.mountblue.reddit.redditClone.repository.FlairRepository;
import io.mountblue.reddit.redditClone.repository.SubRedditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class FlairManager implements FlairService{
    private FlairRepository flairRepository;
    private SubRedditRepository subRedditRepository;

    @Override
    public List<String> fetchSubredditFlairNames(String subRedditName){
        List<String> relativeFlairNames = new ArrayList<>();
        flairRepository.findFlairsBySubReddit(subRedditRepository.findSubRedditBySubRedditName(subRedditName)
                .orElseThrow(() -> new SubRedditNotFound("No Subreddit with name : " + subRedditName)))
                .forEach(flair -> {
                    relativeFlairNames.add(flair.getFlairName());
                });
        return relativeFlairNames;
    }

    @Override
    public List<Flair> fetchFlairByFlairName(List<String> flairNames){
        return flairRepository.findFlairsByFlairNameInOrderByFlairName(flairNames);
    }

}
