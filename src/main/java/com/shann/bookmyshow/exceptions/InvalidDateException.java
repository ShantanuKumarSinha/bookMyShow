package com.shann.bookmyshow.exceptions;

public class InvalidDateException extends Exception {
    public InvalidDateException() {
        super("Invalid Date");
    }

    public InvalidDateException(String message) {
        super(message);
    }
}
