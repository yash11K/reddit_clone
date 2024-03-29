package io.mountblue.reddit.redditClone.exception;

public class PostNotFound extends RuntimeException{
    public PostNotFound() {
    }

    public PostNotFound(String message) {
        super(message);
    }
}
