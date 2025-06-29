package com.example.progress.model;

public class UserTVShowTracker {

    private int id; // primary key of tracker row
    private int userId; // foreign key → User table
    private int tvShowId; // foreign key → TVShow table
    private int progress; // how many episodes watched
    private String status; // "Plan to Watch", "Watching", "Completed"
    private Integer rating; // can be null (Integer wrapper allows that)


    // CONSTRUCTORS
    public UserTVShowTracker() {}

    public UserTVShowTracker(int id, int userId, int tvShowId, int progress, String status,
            Integer rating) {
        this.id = id;
        this.userId = userId;
        this.tvShowId = tvShowId;
        this.progress = progress;
        this.status = status;
        this.rating = rating;
    }

    // GETTERS & SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTvShowId() {
        return tvShowId;
    }

    public void setTvShowId(int tvShowId) {
        this.tvShowId = tvShowId;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    // toString()——for console/debugging
    @Override
    public String toString() {
        return "UserTVShowTracker [id=" + id + ", userId=" + userId + ", tvShowId=" + tvShowId
                + ", progress=" + progress + ", status=" + status + ", rating=" + rating + "]";
    }
}
