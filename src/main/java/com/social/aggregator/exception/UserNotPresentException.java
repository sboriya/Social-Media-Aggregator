package com.social.aggregator.exception;

public class UserNotPresentException extends RuntimeException {
    public UserNotPresentException(String username) {
        super("User '" + username + "' not presnt, please create user");
    }
}
