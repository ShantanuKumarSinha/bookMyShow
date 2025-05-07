package com.shann.bookmyshow.services;

import com.shann.bookmyshow.entities.User;

public interface UserService {

    public User createUser(String username, String password, String email);

    public boolean login(String email, String password) throws Exception;

}
