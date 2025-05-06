package com.shann.bookmyshow.dtos;

public record SignUpResponseDTO(String username, String password, String email, ResponseStatusDTO status) {
}
