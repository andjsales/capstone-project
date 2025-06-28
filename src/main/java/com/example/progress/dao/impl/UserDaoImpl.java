// Each class should implement the methods from its interface using JDBC + ConnectionManager

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

public class UserDaoImpl implements UserDao {

    @Override
    public User findByUsernameAndPassword(String username, String password) {

        // SQL statement to check for a match
        String sql = "SELECT * FROM User WHERE username = ? AND password = ?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Bind values to the placeholders
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery(); // run the SELECT

            // If a matching user is found, create and return User object
            if (rs.next()) {
                int id = rs.getInt("id");
                String user = rs.getString("username");
                String pass = rs.getString("password");

                return new User(id, user, pass);
            }

        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
        }

        // Return null if no match was found
        return null;
    }

    private void getConnection() {
        // TODO
    }
}

