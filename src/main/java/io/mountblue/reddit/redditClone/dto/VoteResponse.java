package io.mountblue.reddit.redditClone.dto;

import io.mountblue.reddit.redditClone.model.VOTE_TYPE;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteResponse {
    private int voteCount;
    private VOTE_TYPE userVote;
}
