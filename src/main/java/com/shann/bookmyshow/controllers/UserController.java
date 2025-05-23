package com.shann.bookmyshow.controllers;

import com.shann.bookmyshow.dtos.*;
import com.shann.bookmyshow.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signUp")
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto signUpRequestDTO) {
        // Call the user service to create a new user
        try {
            var user = userService.createUser(signUpRequestDTO.name(), signUpRequestDTO.password(), signUpRequestDTO.email(),signUpRequestDTO.userType());

            // Create a response DTO
            var responseDTO = new SignUpResponseDto(user.getUsername(), user.getPassword(), user.getEmail(), ResponseStatus.SUCCESS);
            return responseDTO;
        } catch (Exception e) {
            // Handle the exception and create a failure response DTO
            var responseDTO = new SignUpResponseDto(null, null, null, ResponseStatus.FAILURE);
            // Return the response DTO
            return responseDTO;
        }

    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto requestDto) {
        var responseDto = new LoginResponseDto();
        try {
            // Call the user service to login
            boolean loggedIn = userService.login(requestDto.getEmail(), requestDto.getPassword());
            responseDto.setLoggedIn(loggedIn);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            // Handle the exception and create a failure response DTO
            responseDto.setLoggedIn(false);
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
