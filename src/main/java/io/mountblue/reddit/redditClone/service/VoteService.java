package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.model.Post;

public interface VoteService {
    void votes(Post post, String voteType);
}
