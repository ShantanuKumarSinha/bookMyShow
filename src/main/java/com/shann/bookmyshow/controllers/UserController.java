package com.shann.bookmyshow.controllers;

import com.shann.bookmyshow.dtos.ResponseStatusDTO;
import com.shann.bookmyshow.dtos.SignUpRequestDTO;
import com.shann.bookmyshow.dtos.SignUpResponseDTO;
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
    public SignUpResponseDTO signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        // Call the user service to create a new user
        try {
            var user = userService.createUser(signUpRequestDTO.name(), signUpRequestDTO.password(), signUpRequestDTO.email());

            // Create a response DTO
            var responseDTO = new SignUpResponseDTO(user.getUsername(), user.getPassword(), user.getEmail(), ResponseStatusDTO.SUCCESS);
            return responseDTO;
        } catch (Exception e) {
            // Handle the exception and create a failure response DTO
            var responseDTO = new SignUpResponseDTO(null, null, null, ResponseStatusDTO.FAILURE);
            // Return the response DTO
            return responseDTO;
        }

    }
}
