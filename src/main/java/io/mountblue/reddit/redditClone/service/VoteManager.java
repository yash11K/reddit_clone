package io.mountblue.reddit.redditClone.service;

import io.mountblue.reddit.redditClone.model.Post;
import io.mountblue.reddit.redditClone.model.Vote;
import io.mountblue.reddit.redditClone.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@AllArgsConstructor
public class VoteManager implements VoteService{
    private PostRepository postRepository;
    @Override
    public void votes(Post post, String voteType) {
        System.out.println(voteType);
        Long voteCountOnPost = post.getVoteCount();
        if(Objects.equals(voteType, "UPVOTE")) {
            voteCountOnPost += 1L;
        }
        else
            voteCountOnPost -= 1L;
        post.setVoteCount(voteCountOnPost);
        postRepository.save(post);
        System.out.println(voteCountOnPost);
    }
}
