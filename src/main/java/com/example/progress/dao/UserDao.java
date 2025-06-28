package com.example.progress.dao;

import com.example.progress.model.User;

public interface UserDao {

    User findByUsernameAndPassword(String username, String password);

}
