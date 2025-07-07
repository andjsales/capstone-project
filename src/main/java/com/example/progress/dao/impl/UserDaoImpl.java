package com.example.progress.dao.impl;

import com.example.progress.connection.ConnectionManager;
import com.example.progress.dao.UserDao;
import com.example.progress.model.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.progress.exception.AuthenticationException;


public class UserDaoImpl implements UserDao {


    @Override
    public User findByUsernameAndPassword(String username, String password)
            throws AuthenticationException {
        String sql = "SELECT * FROM User WHERE username = ? AND password = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String user = rs.getString("username");
                String pass = rs.getString("password");
                boolean isAdmin = rs.getBoolean("is_admin");
                return new User(id, user, pass, isAdmin);
            } else {
                throw new AuthenticationException("Incorrect username or password.");
            }
        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
            throw new AuthenticationException("Error during login.");
        }
    }

    private void getConnection() {
        // TODO
    }
}

