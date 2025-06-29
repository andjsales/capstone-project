package com.example.progress;

import com.example.progress.dao.UserDao;
import com.example.progress.dao.impl.UserDaoImpl;
import com.example.progress.model.TVShow;
import com.example.progress.model.User;
import com.example.progress.model.UserTVShowTracker;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import com.example.progress.connection.ConnectionManager;
import com.example.progress.dao.TVShowDao;
import com.example.progress.dao.TrackerDao;
import com.example.progress.dao.impl.TVShowDaoImpl;
import com.example.progress.dao.impl.TrackerDaoImpl;


public class App {

    public static void main(String[] args)
            throws FileNotFoundException, IOException, ClassNotFoundException {

        // LOGIC TO CONNECT TO DATABASE
        try {
            Connection conn = ConnectionManager.getConnection();
            System.out.println("\nConnected to the database!");
        } catch (SQLException e) {
            System.out.println("\nFailed to connect to the database.");
            e.printStackTrace();
        } ;

        Scanner scanner = new Scanner(System.in);
        UserDao userDao = new UserDaoImpl(); // USE DAO TO CHECK LOGIN
        User loggedInUser = null; // STORING USER INFO

        // ———————————————————————————————————————————————————————————————————————
        // Login and Exit options

        System.out.println("\n===== Your Personal Watchlist =====");

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Exit");
            System.out.print("\nChoose an option: \n");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.print("\nUsername: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();

                // check credentials using the DAO
                loggedInUser = userDao.findByUsernameAndPassword(username, password);

                if (loggedInUser != null) {
                    System.out.println("\n-----------------" + "\n\nWELCOME, "
                            + loggedInUser.getUsername() + "!");
                    break; // exit login loop ——> continue

                    // You are now logged in!

                } else {
                    System.out.println("Invalid login. Please try again.");
                }

            } else if (choice.equals("2")) {
                System.out.println("Exiting program. Goodbye!");
                System.exit(0);
            } else {
                System.out.println("Invalid input. Please choose 1 or 2.");
            }
        }

        // ———————————————————————————————————————————————————————————————————————
        // ——show menu using loggedInUser.getId()

        loggedInUser.getId();
        System.out.println("(User ID: " + loggedInUser.getId() + ")");

        // ———————————————————————————————————————————————————————————————————————
        // output a list of currently all watching tv shows in alphabetical order of their titles
        // prints——title, status, progress, rating

        TrackerDao trackerDao = new TrackerDaoImpl();
        TVShowDao tvShowDao = new TVShowDaoImpl();

        int userId = loggedInUser.getId();
        List<UserTVShowTracker> findAllWatching = trackerDao.findAllWatchingAlphabetically(userId);

        System.out.println("\n1. View all shows");
        System.out.println("2. View all currently watching shows");
        System.out.print("\nChoose an option: \n");

        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            System.out.println("\n-----------------");
            System.out.println("\nAll Shows——\n");
            List<TVShow> allShows = tvShowDao.findAllTVShows();
            for (TVShow show : allShows) {
                System.out.println("Title: " + show.getTitle());
                System.out.println("Total Episodes: " + show.getTotalEpisodes());
                System.out.println();
            }
        } else if (choice.equals("2")) {
            if (findAllWatching.isEmpty()) {
                System.out.println("\n-----------------");
                System.out.println("\nYou are not currently watching any shows.");
            } else {
                System.out.println("\n-----------------");
                System.out.println("\nCurrently Watching Shows——\n");
                for (UserTVShowTracker tracker : findAllWatching) {
                    TVShow show = tvShowDao.findAllTVShows(tracker.getTvShowId());
                    System.out.println("Title: " + show.getTitle());
                    System.out.println("Progress: " + tracker.getProgress());
                    System.out.println("Status: " + tracker.getStatus());
                    System.out.println("Rating: " + tracker.getRating());
                    System.out.println();
                }
            }
        } else {
            System.out.println("Invalid entry. Please choose 1 or 2.");
        }
        // list ALL tv shows
    }

}
