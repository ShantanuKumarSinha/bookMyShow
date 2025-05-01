package com.shann.bookmyshow.service;

import com.shann.bookmyshow.entity.User;

public interface UserService {

    public User createUser(String username, String password, String email);
}
