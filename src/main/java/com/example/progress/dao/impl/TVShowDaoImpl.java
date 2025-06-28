// Each class should implement the methods from its interface using JDBC + ConnectionManager

package com.example.progress.dao.impl;

import com.example.progress.dao.TVShowDao;
import com.example.progress.model.TVShow;
// import java.sql.SQLException;


public class TVShowDaoImpl implements TVShowDao {
    // @SuppressWarnings("unused")
    @Override
    public TVShow findTVShowByTitle(int id, String title, int totalEpisodes) {
        String sql = "SELECT * FROM TVShow WHERE id = ? AND title = ? AND totalEpisodes";
        return null;
    }
}
