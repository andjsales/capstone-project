package com.example.progress.dao;

import com.example.progress.model.User;

public interface UserDao {

    // Try to find a user with the matching username/password
    User findByUsernameAndPassword(String username, String password);
}
