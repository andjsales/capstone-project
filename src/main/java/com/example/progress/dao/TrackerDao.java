package com.example.progress.dao;

import com.example.progress.model.UserTVShowTracker;
import com.example.progress.exception.TrackerNotFoundException;
import java.util.List;

public interface TrackerDao {
    UserTVShowTracker findUserTrackerByUserId(int userId, int tvShowId)
            throws TrackerNotFoundException;

    List<UserTVShowTracker> findAllByStatus(int userId, String status);

    List<UserTVShowTracker> findAllOrderByRating(int userId);

    List<UserTVShowTracker> findAllWatchingAlphabetically(int userId);

    void addUserTVShowTracker(int userId, int tvShowId, int progress, String status,
            Integer rating);

    void updateRating(int userId, int tvShowId, Integer rating);

    // average rating for a show
    public Double getAverageRatingForShow(int tvShowId);

    // user counts by status
    public int countUsersByStatus(int tvShowId, String status);
}
