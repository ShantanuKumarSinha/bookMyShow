package com.shann.bookmyshow.exceptions;

public class UnAuthorizedAccessException extends Exception {
    public UnAuthorizedAccessException() {
        super("UnAuthorized Access");
    }

    public UnAuthorizedAccessException(String message) {
        super(message);
    }
}
