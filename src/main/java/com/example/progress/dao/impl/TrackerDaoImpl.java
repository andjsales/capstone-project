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
import com.example.progress.model.TVShow;
import com.example.progress.model.UserTVShowTracker;



public class TrackerDaoImpl implements TrackerDao {

    // MARK: findUserTrackerByUserId
    @Override
    public UserTVShowTracker findUserTrackerByUserId(int userId, int tvShowId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUserTrackerByUserId'");
    }

    // MARK: findAllByStatus
    @Override
    public List<UserTVShowTracker> findAllByStatus(int userId, String status) {
        List<UserTVShowTracker> trackers = new ArrayList<>();
        String sql =
                "SELECT t.* FROM UserTVShowTracker t " + "JOIN TVShow s ON t.tv_show_id = s.id "
                        + "WHERE t.user_id = ? AND t.status = ? " + "ORDER BY s.title ASC";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, status); // use the string directly
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trackers;
    }



    // MARK: findAllOrderByRating
    @Override
    public List<UserTVShowTracker> findAllOrderByRating(int userId) {

        List<UserTVShowTracker> trackers = new ArrayList<>();
        String sql = "SELECT t.* FROM UserTVShowTracker t " + //
                "JOIN TVShow s ON t.tv_show_id = s.id " + //
                "WHERE t.user_id = ? AND t.status = 'Watching'" + //
                "ORDER BY t.rating DESC"; //

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

    // MARK: findAllWatchingAlphabetically
    @Override
    public List<UserTVShowTracker> findAllWatchingAlphabetically(int userId) {

        // define an empty list to hold results
        // will contain matching UserTVShowTracker objects for the given userId
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

    // MARK: addUserTVShowTracker
    @Override
    public void addUserTVShowTracker(int userId, int tvShowId, int progress, String status,
            Integer rating) {
        String sql =
                "INSERT INTO UserTVShowTracker (user_id, tv_show_id, progress, status, rating) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, tvShowId);
            pstmt.setInt(3, progress);
            pstmt.setString(4, status);
            if (rating != null) {
                pstmt.setInt(5, rating);
            } else {
                pstmt.setNull(5, java.sql.Types.INTEGER);
            }
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MARK: updateRating
    @Override
    public void updateRating(int userId, int tvShowId, Integer rating) {
        String sql = "UPDATE UserTVShowTracker SET rating = ? WHERE user_id = ? AND tv_show_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (rating == null) {
                stmt.setNull(1, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(1, rating);
            }
            stmt.setInt(2, userId);
            stmt.setInt(3, tvShowId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MARK: getAverageRatingForShow
    public Double getAverageRatingForShow(int tvShowId) {
        String sql =
                "SELECT AVG(rating) FROM UserTVShowTracker WHERE tv_show_id = ? AND rating IS NOT NULL";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tvShowId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getDouble(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // MARK: countUsersByStatus
    public int countUsersByStatus(int tvShowId, String status) {
        String sql = "SELECT COUNT(*) FROM UserTVShowTracker WHERE tv_show_id = ? AND status = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tvShowId);
            stmt.setString(2, status);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
