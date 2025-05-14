package com.shann.bookmyshow.services.impl;

import com.shann.bookmyshow.entities.User;
import com.shann.bookmyshow.enums.UserType;
import com.shann.bookmyshow.exceptions.UserNotFoundException;
import com.shann.bookmyshow.repositories.UserRepository;
import com.shann.bookmyshow.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    /**
     * This method creates a new user in the system.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @param email    The email of the user.
     * @param userType The type of the user (CUSTOMER or ADMIN).
     * @return The created User object.
     */
    @Override
    public User createUser(String username, String password, String email, UserType userType) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new RuntimeException("User already exists with this email");
        });
        var user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setUserType(userType);
        return userRepository.save(user);
    }

    @Override
    public boolean login(String email, String password) throws Exception {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return true;
        }
        return false;
    }
}
