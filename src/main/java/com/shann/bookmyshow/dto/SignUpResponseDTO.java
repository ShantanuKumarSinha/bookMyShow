package com.shann.bookmyshow.dto;

public record SignUpResponseDTO(String username, String password, String email, ResponseStatusDTO status) {
}
