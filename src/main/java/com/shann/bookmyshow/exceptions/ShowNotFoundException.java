package com.shann.bookmyshow.exceptions;

public class ShowNotFoundException extends Exception {
    public ShowNotFoundException() {
        super("Show not Found");
    }
    public ShowNotFoundException(String message) {
        super(message);
    }
}
