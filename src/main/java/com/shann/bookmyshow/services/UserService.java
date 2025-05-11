package com.shann.bookmyshow.services;

import com.shann.bookmyshow.entities.User;
import com.shann.bookmyshow.enums.UserType;

public interface UserService {

    public User createUser(String username, String password, String email, UserType userType);

    public boolean login(String email, String password) throws Exception;

}
