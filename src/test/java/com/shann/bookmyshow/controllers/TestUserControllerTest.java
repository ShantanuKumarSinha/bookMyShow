package com.shann.bookmyshow.controllers;

import com.shann.bookmyshow.dtos.*;
import com.shann.bookmyshow.entities.User;
import com.shann.bookmyshow.enums.UserType;
import com.shann.bookmyshow.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
@TestPropertySource("classpath:application-test.properties")
public class TestUserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
    }

    @Test
    void testSignupUserSuccess() {
        String email = "test@scaler.com";
        String password = "Password@123";
        String name = "Test User";
        SignUpRequestDto requestDTO = new SignUpRequestDto(name, email, password, UserType.CUSTOMER);

        SignUpResponseDto signUpResponseDto = userController.signUp(requestDTO);
        assertEquals(ResponseStatus.SUCCESS, signUpResponseDto.status());
        assertEquals(email, signUpResponseDto.email(), "Email should match");
        assertEquals(name, signUpResponseDto.username(), "Name should match");
        User user = userRepository.findAll().stream().findFirst().orElse(null);
        assertNotNull(user);
        assertEquals(email, user.getEmail(), "Email should match");
        assertEquals(name, user.getUsername(), "Name should match");
        assertNotNull(user.getPassword(), "Password should not be null");
        assertNotEquals(password, user.getPassword(), "Password should be encrypted");
    }

    @Test
    public void testSignupUser_SameUser_RegisteringTwice_Failure() {
        String email = "test@scaler.com";
        String password = "Password@123";
        String name = "Test User";
        SignUpRequestDto requestDTO = new SignUpRequestDto(name, email, password, UserType.CUSTOMER);

        SignUpResponseDto signUpResponseDto = userController.signUp(requestDTO);
        assertEquals(ResponseStatus.SUCCESS, signUpResponseDto.status());
        assertEquals(email, signUpResponseDto.email(), "Email should match");
        assertEquals(name, signUpResponseDto.username(), "Name should match");

        signUpResponseDto = userController.signUp(requestDTO);
        assertEquals(ResponseStatus.FAILURE, signUpResponseDto.status(), "Response status should be FAILURE");
        assertNull(signUpResponseDto.email(), "Email should be null");
        assertNull(signUpResponseDto.username(), "Name should be null");
    }

    @Test
    public void testSignupUserSuccess_AndLoginSuccess() {
        String email = "test@scaler.com";
        String password = "Password@123";
        String name = "Test User";
        SignUpRequestDto requestDTO = new SignUpRequestDto(name, email, password, UserType.CUSTOMER);

        SignUpResponseDto signUpResponseDto = userController.signUp(requestDTO);
        assertEquals(ResponseStatus.SUCCESS, signUpResponseDto.status());
        assertEquals(email, signUpResponseDto.email());
        assertEquals(name, signUpResponseDto.username());

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail(email);
        loginRequestDto.setPassword(password);
        LoginResponseDto loginResponseDto = userController.login(loginRequestDto);
        assertTrue(loginResponseDto.isLoggedIn());
        assertEquals(ResponseStatus.SUCCESS, loginResponseDto.getResponseStatus());
    }

    @Test
    public void testSignupUserSuccess_AndLoginWithIncorrectPassword() {
        String email = "test@scaler.com";
        String password = "Password@123";
        String name = "Test User";
        SignUpRequestDto requestDTO = new SignUpRequestDto(name, email, password, UserType.CUSTOMER);

        SignUpResponseDto signUpResponseDto = userController.signUp(requestDTO);
        assertEquals(ResponseStatus.SUCCESS, signUpResponseDto.status());
        assertEquals(email, signUpResponseDto.email());
        assertEquals(name, signUpResponseDto.username());

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail(email);
        loginRequestDto.setPassword(password + "1");
        LoginResponseDto loginResponseDto = userController.login(loginRequestDto);
        assertFalse(loginResponseDto.isLoggedIn());
        assertEquals(ResponseStatus.SUCCESS, loginResponseDto.getResponseStatus());
    }

    @Test
    public void testSignupUser_UserAlreadyExists() {
        String email = "test@scaler.com";
        String password = "Password@123";
        String name = "Test User";
        SignUpRequestDto requestDTO = new SignUpRequestDto(name, email, password, UserType.CUSTOMER);

        //User registering for the first time
        SignUpResponseDto signUpResponseDto = userController.signUp(requestDTO);

        //User registering for the second time
        signUpResponseDto = userController.signUp(requestDTO);
        assertEquals(ResponseStatus.FAILURE, signUpResponseDto.status());
        assertNull(signUpResponseDto.email());
        assertNull(signUpResponseDto.username());
    }

    @Test
    public void testLogin_UserNotSignedUp() {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        String email = "test@scaler.com";
        String password = "Password@123";
        loginRequestDto.setEmail(email);
        loginRequestDto.setPassword(password);

        LoginResponseDto loginResponseDto = userController.login(loginRequestDto);
        assertFalse(loginResponseDto.isLoggedIn());
        assertEquals(ResponseStatus.FAILURE, loginResponseDto.getResponseStatus());
    }
}

