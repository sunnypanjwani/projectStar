package com.stars.exception;

public class InvalidSubscriptionException extends Exception {
	private static final long serialVersionUID = 1L;

    public InvalidSubscriptionException(String string) {
            super(string);
    }
}