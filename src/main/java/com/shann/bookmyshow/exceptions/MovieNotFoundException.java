package com.shann.bookmyshow.exceptions;

public class MovieNotFoundException extends Exception {

    public MovieNotFoundException() {
        super("Movie not Found");
    }

    public MovieNotFoundException(String message) {
        super(message);
    }
}
