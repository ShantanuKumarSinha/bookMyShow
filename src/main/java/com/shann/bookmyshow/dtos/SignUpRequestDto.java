package com.shann.bookmyshow.dtos;


import com.shann.bookmyshow.enums.UserType;

public record SignUpRequestDto(String name, String email, String password, UserType userType) { }
