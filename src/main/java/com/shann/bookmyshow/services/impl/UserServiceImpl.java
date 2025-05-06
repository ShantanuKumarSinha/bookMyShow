package com.shann.bookmyshow.services.impl;

import com.shann.bookmyshow.entities.User;
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
     * @return The created User object.
     */
    @Override
    public User createUser(String username, String password, String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new RuntimeException("User already exists with this email");
        });
        var user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        return userRepository.save(user);
    }
}
