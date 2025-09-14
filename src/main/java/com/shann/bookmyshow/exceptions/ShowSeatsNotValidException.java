package com.shann.bookmyshow.exceptions;

public class ShowSeatsNotValidException extends RuntimeException {
    public ShowSeatsNotValidException(String s) {
        super("All Show Seats Are Not Valid");
    }
}
