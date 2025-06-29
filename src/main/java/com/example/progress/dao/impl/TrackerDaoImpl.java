// Each class should implement the methods from its interface using JDBC + ConnectionManager

package com.example.progress.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.example.progress.connection.ConnectionManager;
import com.example.progress.dao.TrackerDao;
import com.example.progress.model.UserTVShowTracker;
import com.example.progress.model.WatchStatus;



public class TrackerDaoImpl implements TrackerDao {

    @Override
    public UserTVShowTracker findUserTrackerByUserId(int userId, int tvShowId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUserTrackerByUserId'");
    }

    @Override
    public List<UserTVShowTracker> findAllByStatus(int userId, WatchStatus status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllByStatus'");
    }

    @Override
    public List<UserTVShowTracker> findAllOrderByRating(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllOrderByRating'");
    }

    // findAllWatchingAlphabetically()
    @Override
    public List<UserTVShowTracker> findAllWatchingAlphabetically(int userId) {

        List<UserTVShowTracker> trackers = new ArrayList<>();

        // look for rows in UserTVShowTracker for user_id
        String sql =
                "SELECT t.* FROM UserTVShowTracker t " + "JOIN TVShow s ON t.tv_show_id = s.id "
                        + "WHERE t.user_id = ? AND t.status = 'Watching'" + "ORDER BY s.title ASC";


        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                UserTVShowTracker tracker = new UserTVShowTracker();
                tracker.setId(rs.getInt("id"));
                tracker.setUserId(rs.getInt("user_id"));
                tracker.setTvShowId(rs.getInt("tv_show_id"));
                tracker.setProgress(rs.getInt("progress"));
                tracker.setStatus(rs.getString("status"));
                tracker.setRating(rs.getObject("rating") != null ? rs.getInt("rating") : null);
                trackers.add(tracker);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return trackers;
    }
}
