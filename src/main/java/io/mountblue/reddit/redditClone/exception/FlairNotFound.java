package io.mountblue.reddit.redditClone.exception;

public class FlairNotFound extends RuntimeException{
    public FlairNotFound() {
    }

    public FlairNotFound(String message) {
        super(message);
    }
}
