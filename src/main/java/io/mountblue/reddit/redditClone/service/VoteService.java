package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.model.Post;
import io.mountblue.reddit.redditClone.model.User;
import io.mountblue.reddit.redditClone.model.Vote;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface VoteService {
    Optional<Vote> findVoteExist(Post post, User user);

    void delete(Vote vote);

    void save(Vote vote);
}
