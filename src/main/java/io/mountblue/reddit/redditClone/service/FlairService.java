package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.model.Flair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlairService {
    List<String> fetchSubredditFlairNames(String subRedditName);

    List<Flair> fetchFlairByFlairName(List<String> flairNames);
}
