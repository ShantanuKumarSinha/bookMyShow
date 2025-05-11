package com.shann.bookmyshow.dtos;

public record SignUpResponseDto(String username, String password, String email, ResponseStatus status) {
}
