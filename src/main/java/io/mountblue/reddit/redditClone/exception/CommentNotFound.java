package io.mountblue.reddit.redditClone.exception;

public class CommentNotFound extends RuntimeException{
    public CommentNotFound() {
    }

    public CommentNotFound(String message) {
        super(message);
    }
}
