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
import java.util.ArrayList;
import java.util.List;


public class TVShowDaoImpl implements TVShowDao {

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
    public List<TVShow> findAllTVShows() {
        List<TVShow> shows = new ArrayList<>();
        String sql = "SELECT * FROM TVShow";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                int totalEpisodes = rs.getInt("total_episodes");
                shows.add(new TVShow(id, title, totalEpisodes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shows;
    }


    @Override
    public TVShow findTVShowByTitle(int id, String title, int total_episodes) {
        return null;
        // TODO
    }
}
