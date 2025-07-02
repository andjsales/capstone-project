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
    // helper method to get user's progress for a show
    private static int getUserProgressForShow(List<UserTVShowTracker> trackers, int tvShowId) {
        for (UserTVShowTracker tracker : trackers) {
            if (tracker.getTvShowId() == tvShowId) {
                return tracker.getProgress();
            }
        }
        return 0;
    }

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
                    System.out.println("\n—————————————————————————————————" + "\n\nWELCOME, "
                            + loggedInUser.getUsername() + "!");
                    break; // exit login loop ——> continue

                    // You are now logged in!

                } else {
                    System.out.println("\nIncorrect login——please try again.");
                }

            } else if (choice.equals("2")) {
                System.out.println("Exiting program. Goodbye!");
                System.exit(0);
            } else {
                System.out.println("\nWrong entry. Please choose 1 or 2.");
            }
        }
        while (true) {
            // ——show menu using loggedInUser.getId()

            loggedInUser.getId();
            System.out.println("(User ID: " + loggedInUser.getId() + ")");

            // output all currently watching tv shows——alphabetical

            // prints——title, status, progress, rating

            TrackerDao trackerDao = new TrackerDaoImpl();
            TVShowDao tvShowDao = new TVShowDaoImpl();

            int userId = loggedInUser.getId();

            List<UserTVShowTracker> sortByTitle = trackerDao.findAllWatchingAlphabetically(userId);
            List<UserTVShowTracker> sortByRating = trackerDao.findAllOrderByRating(userId);
            List<TVShow> allShows = tvShowDao.findTVShowById();

            System.out.println("\n—————————————————————————————————");
            System.out.println("\n1. Add to watchlist");
            System.out.println("2. View All Shows");
            System.out.println("3. Sort Currently Watching Alphabetically");
            System.out.println("4. Sort Currently Watching by Rating");
            System.out.println("5. Exit");
            System.out.print("\nChoose an option: ");

            String choice = scanner.nextLine();

            // ADDING A NEW TV SHOW INTO THE DATABASE
            // prompt user for——title——progress——status——rating

            // 1
            if (choice.equals("1")) {
                System.out.println("\nAdd a new TV Show to your watchlist——\n");
                System.out.println("Enter title: ");
                String addTitle = scanner.nextLine();
                System.out.print("Enter total number of episodes: ");
                int addTotalEpisodes = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter episodes watched: ");
                int addProgress = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter watching status (Plan to Watch, Watching, Completed): ");
                String addStatus = scanner.nextLine();
                System.out.println("Enter rating (optional): ");
                String ratingInput = scanner.nextLine();
                Integer addRating = ratingInput.isEmpty() ? null : Integer.parseInt(ratingInput);

                TVShow newShow = tvShowDao.addTVShow(addTitle, addTotalEpisodes);
                if (newShow == null) {
                    System.out.println("Failed to add.");
                    return;
                }
                TVShow showFromDb = tvShowDao.findTVShowByTitle(addTitle);
                if (showFromDb == null) {
                    System.out.println("Could not find new show in the database.");
                    return;
                }
                trackerDao.addUserTVShowTracker(loggedInUser.getId(), showFromDb.getId(),
                        addProgress, addStatus, addRating);
                System.out.println("Show added successfully!");
            }


            // 2
            if (choice.equals("2")) {

                // System.out.println("\n—————————————————————————————————");
                System.out.println("\nVIEW ALL SHOWS——\n");

                for (TVShow show : allShows) {
                    int progress = getUserProgressForShow(sortByTitle, show.getId());
                    System.out.println('"' + show.getTitle() + '"' + " — " + progress + "/"
                            + show.getTotalEpisodes());
                }

                // 3
            } else if (choice.equals("3")) {
                if (sortByTitle.isEmpty()) {
                    // System.out.println("\n—————————————————————————————————");
                    System.out.println("\nYou are not currently watching any shows or movies.");
                } else {
                    // System.out.println("\n—————————————————————————————————");
                    System.out.println("\nALL CURRENTLY WATCHING (Sorted alphabetically)——\n");
                    for (UserTVShowTracker tracker : sortByTitle) {
                        TVShow show = tvShowDao.findTVShowById(tracker.getTvShowId());
                        System.out.println("Title: " + show.getTitle());
                        System.out.println("Progress: " + tracker.getProgress() + "/"
                                + show.getTotalEpisodes());
                        System.out.println("Status: " + tracker.getStatus());
                        System.out.println("Rating: " + tracker.getRating());
                        System.out.println();
                    }
                }

                // 4
            } else if (choice.equals("4")) {
                // System.out.println("\n—————————————————————————————————");
                System.out.println("\nALL CURRENTLY WATCHING (Sorted by rating)——\n");
                for (UserTVShowTracker tracker : sortByRating) {
                    TVShow show = tvShowDao.findTVShowById(tracker.getTvShowId());
                    System.out.println("Title: " + show.getTitle());
                    System.out.println(
                            "Progress: " + tracker.getProgress() + "/" + show.getTotalEpisodes());
                    System.out.println("Status: " + tracker.getStatus());
                    System.out.println("Rating: " + tracker.getRating());
                    System.out.println();
                }

            } else if (choice.equals("5")) {
                break;
            } else {
                System.out.println("Wrong entry, please try again.");
            }



            // look up show in TVShow table——use tltle user provided

            // create/update UserTVShowTracker entry for this user and show
        }
    }
}
