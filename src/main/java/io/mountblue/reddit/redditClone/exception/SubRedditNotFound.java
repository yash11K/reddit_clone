package io.mountblue.reddit.redditClone.exception;

public class SubRedditNotFound extends RuntimeException{
    public SubRedditNotFound() {
    }

    public SubRedditNotFound(String message) {
        super(message);
    }
}
