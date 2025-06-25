package com.example.progress.model;

public class User {

    private int id; // maps to SQL column `id`
    private String username; // maps to SQL column `username`
    private String password; // maps to SQL column `password`

    // Empty CONSTRUCTOR for JDBC or frameworks
    public User() {}

    // CONSTRUCTOR for creating new User objects quickly
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }



    // Getters & Setters (used to read/write the fields)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString() = how it prints in the console/debugger
    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + "]";
    }
}
