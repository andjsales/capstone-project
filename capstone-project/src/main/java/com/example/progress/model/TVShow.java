package com.example.progress.model;

public class TVShow {

    // === FIELDS ===
    private int id; // maps to TVShow table's `id` column
    private String title; // maps to `title` column
    private int totalEpisodes; // maps to `total_episodes` column

    // === CONSTRUCTORS ===

    // Empty constructor required by frameworks/JDBC
    public TVShow() {}

    // Full constructor to quickly create TVShow objects
    public TVShow(int id, String title, int totalEpisodes) {
        this.id = id;
        this.title = title;
        this.totalEpisodes = totalEpisodes;
    }

    // === GETTERS & SETTERS ===

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
        return totalEpisodes;
    }

    public void setTotalEpisodes(int totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }

    // === toString() for debugging/logging ===
    @Override
    public String toString() {
        return "TVShow [id=" + id + ", title=" + title + ", totalEpisodes=" + totalEpisodes + "]";
    }
}

