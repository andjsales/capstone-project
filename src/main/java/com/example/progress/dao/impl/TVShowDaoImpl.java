// Each class should implement the methods from its interface using JDBC + ConnectionManager

package com.example.progress.dao.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.progress.connection.ConnectionManager;
import com.example.progress.dao.TVShowDao;
import com.example.progress.model.TVShow;
// import java.sql.SQLException;
// import com.example.progress.model.User;


public class TVShowDaoImpl implements TVShowDao {
    // @Override
    // public TVShow findTVShowByTitle(int id, String title, int totalEpisodes) {
    // String sql = "SELECT * FROM TVShow WHERE id = ? AND title = ? AND totalEpisodes = ?";

    // try (Connection conn = ConnectionManager.getConnection();
    // PreparedStatement stmt = conn.prepareStatement(sql)) {
    // stmt.setInt(1, id);
    // stmt.setString(2, title);
    // stmt.setInt(3, totalEpisodes);

    // ResultSet rs = stmt.executeQuery();

    // if (rs.next()) {
    // int dbId = rs.getInt("id");
    // String dbTitle = rs.getString("title");
    // int dbTotalEpisodes = rs.getInt(totalEpisodes);

    // System.out.println(new TVShow(dbId, dbTitle, dbTotalEpisodes));
    // // return new TVShow(dbId, dbTitle, dbTotalEpisodes);
    // }

    // } catch (SQLException e) {
    // System.out.println("Error finding user by credentials.");
    // e.printStackTrace();
    // } catch (FileNotFoundException e) {
    // System.out.println("Database configuration file not found.");
    // e.printStackTrace();
    // } catch (IOException e) {
    // System.out.println("IO Exception occurred while getting connection.");
    // e.printStackTrace();
    // } catch (ClassNotFoundException e) {
    // System.out.println("JDBC Driver class not found.");
    // e.printStackTrace();
    // }
    // return null;

    // }

    @Override
    public TVShow findAllTVShows(int id) {
        String sql = "SELECT * FROM TVShow WHERE id = ?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int dbId = rs.getInt("id");
                String dbTitle = rs.getString("title");
                int dbTotalEpisodes = rs.getInt("total_episodes");
                return new TVShow(dbId, dbTitle, dbTotalEpisodes);
            }

        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public TVShow findTVShowByTitle(int id, String title, int total_episodes) {
        return null;
        // TODO
    }
}
