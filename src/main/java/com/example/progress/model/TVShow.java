package com.example.progress.model;

public class TVShow {

    private int id; // maps to TVShow table's `id` column
    private String title; // maps to `title` column
    private int total_episodes; // maps to `total_episodes` column


    // defining an empty CONSTRUCTOR——required by frameworks/JDBC
    public TVShow() {}

    // defining a full CONSTRUCTOR to quickly create TVShow objects
    public TVShow(int id, String title, int total_episodes) {
        this.id = id;
        this.title = title;
        this.total_episodes = total_episodes;
    }

    // GETTERS & SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalEpisodes() {
        return total_episodes;
    }

    public void setTotalEpisodes(int total_episodes) {
        this.total_episodes = total_episodes;
    }

    // toString()——for debugging/logging
    @Override
    public String toString() {
        return "TVShow [id=" + id + ", title=" + title + ", total_episodes=" + total_episodes + "]";
    }
}

