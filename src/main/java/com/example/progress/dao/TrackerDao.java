

package com.example.progress.dao;

import com.example.progress.model.UserTVShowTracker;
import com.example.progress.model.WatchStatus;

// public interface TrackerDao {
// // returning a list of all currently watching
// UserTVShowTracker findUserTrackerByUserId(int user_id, int tv_show_id, int progress,
// Enum status, int rating);

// // return a list that gives all of your shows in order of rating

// // return a list of all currently watching alphabetically

// }

import java.util.List;

public interface TrackerDao {
    UserTVShowTracker findUserTrackerByUserId(int userId, int tvShowId);

    List<UserTVShowTracker> findAllByStatus(int userId, WatchStatus status);

    List<UserTVShowTracker> findAllOrderByRating(int userId);

    List<UserTVShowTracker> findAllWatchingAlphabetically(int userId);

}
