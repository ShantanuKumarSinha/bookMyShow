package com.shann.bookmyshow.controller;

import com.shann.bookmyshow.dto.ResponseStatusDTO;
import com.shann.bookmyshow.dto.SignUpRequestDTO;
import com.shann.bookmyshow.dto.SignUpResponseDTO;
import com.shann.bookmyshow.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bookMyShow/api/v1/user")
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
