// defining what your app wants to do with the database—but not how

package com.example.progress.dao;

import java.util.List;
import com.example.progress.model.TVShow;

public interface TVShowDao {

    // JDBC code to query DB and return a TVShow
    TVShow findTVShowByTitle(int id, String title, int total_episodes);

    TVShow findTVShowByTitle(String title);


    // JDBC code to query all shows and return a list
    TVShow findTVShowById(int id);

    List<TVShow> findTVShowById();


    // JDBC code to insert a new show into the database
    TVShow addTVShow(String title, int total_episodes);

}
