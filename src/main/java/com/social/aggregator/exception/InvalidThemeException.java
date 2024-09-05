package com.social.aggregator.exception;

public class InvalidThemeException extends RuntimeException {
    public InvalidThemeException(String theme) {
        super("Invalid theme value: '" + theme + "'. Please provide a valid theme (e.g., 'DARK' or 'LIGHT').");
    }
}