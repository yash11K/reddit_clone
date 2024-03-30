package io.mountblue.reddit.redditClone.model;
import java.util.Arrays;

public enum VOTE_TYPE {
    UPVOTE(1),
    DOWNVOTE(-1);

    private int direction;

    VOTE_TYPE(int direction) {
    }

    public static VOTE_TYPE lookup(Integer direction) {
        return Arrays.stream(VOTE_TYPE.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny().orElseThrow();
    }

    public Integer getDirection() {
        return direction;
    }
}