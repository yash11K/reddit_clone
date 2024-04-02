package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.model.Post;
import io.mountblue.reddit.redditClone.model.User;
import io.mountblue.reddit.redditClone.model.Vote;
import io.mountblue.reddit.redditClone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteManager implements VoteService{
    private final VoteRepository voteRepository;

    @Override
    public Optional<Vote> findVoteExist(Post post, User user){
        return voteRepository.findByPostAndUser(post, user);
    }

    @Override
    public void delete(Vote vote){
        voteRepository.delete(vote);
    }

    @Override
    public void save(Vote vote){
        voteRepository.save(vote);
    }
}
