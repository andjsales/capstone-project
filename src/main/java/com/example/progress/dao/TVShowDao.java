// defining what your app wants to do with the database—but not how

package com.example.progress.dao;

import java.util.List;
import com.example.progress.model.TVShow;

public interface TVShowDao {
    TVShow findTVShowByTitle(int id, String title, int total_episodes);

    TVShow findAllTVShows(int id);

    List<TVShow> findAllTVShows();
}
