package com.shann.bookmyshow.exceptions;

public class ShowSeatsNotValidException extends RuntimeException {
    public ShowSeatsNotValidException() {
        super("All Show Seats Are Not Valid");
    }
}
