package com.shann.bookmyshow.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User not Found");
    }

    public UserNotFoundException(String userNotFound) {
        super(userNotFound);
    }
}
