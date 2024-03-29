package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.dto.SubRedditDto;
import io.mountblue.reddit.redditClone.model.SubReddit;

import java.security.Principal;

public interface SubRedditService {
    SubRedditDto save(SubRedditDto subRedditDto, Principal principal);

    SubRedditDto update(SubRedditDto subRedditDto, String subRedditName);

    SubReddit show(String subRedditName);

    void delete(Long subRedditId);
}
