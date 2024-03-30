package io.mountblue.reddit.redditClone.exception;

public class RuleNotFound extends RuntimeException {
    public RuleNotFound() {
    }

    public RuleNotFound(String message) {
        super(message);
    }
}
