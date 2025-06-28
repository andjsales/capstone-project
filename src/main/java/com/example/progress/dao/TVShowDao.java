// defining what your app wants to do with the database—but not how

package com.example.progress.dao;

import com.example.progress.model.TVShow;

public interface TVShowDao {
    TVShow findTVShowByTitle(int id, String title, int totalEpisodes);

    TVShow findAllTVShows(int id, String title, int totalEpisodes);
}
