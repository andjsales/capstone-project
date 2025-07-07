package com.example.progress.dao;

import com.example.progress.model.User;

import com.example.progress.exception.AuthenticationException;

public interface UserDao {
    User findByUsernameAndPassword(String username, String password) throws AuthenticationException;
}
