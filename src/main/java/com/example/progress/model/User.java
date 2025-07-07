package com.example.progress.model;

public class User {

    private int id; // map to SQL column `id`
    private String username; // map to SQL column `username`
    private String password; // map to SQL column `password`

    // empty constructor—for JDBC/frameworks
    public User() {}

    // constructor——creating new User objects quickly
    public User(int id, String username, String password, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
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

    // admin
    private boolean isAdmin;

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    // toString() = how it prints in the console/debugger
    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + "]";
    }
}
