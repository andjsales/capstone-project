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

        // MARK: LOGIC TO CONNECT TO DATABASE
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
        // MARK: Login + Exit options

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

                // MARK: check credentials using DAO
                loggedInUser = userDao.findByUsernameAndPassword(username, password);

                if (loggedInUser != null) {
                    System.out.println("\n—————————————————————————————————" + "\n\nWELCOME, "
                            + loggedInUser.getUsername() + "!");
                    System.out.println("(User ID: " + loggedInUser.getId() + ")");

                    break; // exit login loop ——> continue

                    // MARK: You are now logged in!

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

            // output all currently watching tv shows——alphabetical

            // prints——title, status, progress, rating

            TrackerDao trackerDao = new TrackerDaoImpl();
            TVShowDao tvShowDao = new TVShowDaoImpl();
            int userId = loggedInUser.getId();
            List<UserTVShowTracker> sortByTitle = trackerDao.findAllWatchingAlphabetically(userId);
            List<UserTVShowTracker> sortByRating = trackerDao.findAllOrderByRating(userId);
            List<TVShow> allShows = tvShowDao.findTVShowById();


            // MARK: —MENU OPTIONS
            System.out.println("\n1. Add to watchlist");
            System.out.println("2. View all saved shows");
            System.out.println("3. Sort currently watching alphabetically");
            System.out.println("4. Sort currently watching by rating");
            System.out.println("5. Filter by watch status");
            System.out.println("6. Exit");
            System.out.println("\n—————————————————————————————————");
            System.out.print("\nChoose an option: ");

            String choice = scanner.nextLine();


            // MARK: 1—ADD TO WATCHLIST
            // prompt user for——title——progress——status——rating
            if (choice.equals("1")) {
                System.out.println("\n1——ADD TO WATCHLIST\n");
                System.out.println("Enter title: ");
                String addTitle = scanner.nextLine();
                System.out.print("Enter total # of episodes: ");
                int addTotalEpisodes = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter # of episodes watched: ");
                int addProgress = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter watching status (planning, watching, completed): ");
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


            // MARK: 2—VIEW ALL SHOWS
            if (choice.equals("2")) {

                System.out.println("\n2——VIEW ALL SAVED SHOWS\n");

                for (TVShow show : allShows) {
                    int progress = getUserProgressForShow(sortByTitle, show.getId());
                    System.out.println('"' + show.getTitle() + '"' + " — " + progress + "/"
                            + show.getTotalEpisodes());
                }

                // MARK: 3—SORT ALPHABETICALLY
            } else if (choice.equals("3")) {
                if (sortByTitle.isEmpty()) {
                    System.out.println("\nYou are not currently watching any shows or movies.");
                } else {
                    System.out.println("\n3——ALL CURRENTLY WATCHING (Sorted alphabetically)\n");
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

                // MARK: 4—SORT BY RATING
            } else if (choice.equals("4")) {
                System.out.println("\n4——ALL CURRENTLY WATCHING (Sorted by rating)\n");
                for (UserTVShowTracker tracker : sortByRating) {
                    TVShow show = tvShowDao.findTVShowById(tracker.getTvShowId());
                    System.out.println("Title: " + show.getTitle());
                    System.out.println(
                            "Progress: " + tracker.getProgress() + "/" + show.getTotalEpisodes());
                    System.out.println("Status: " + tracker.getStatus());
                    System.out.println("Rating: " + tracker.getRating());
                    System.out.println();
                }

                // MARK: 5—FILTER BY WATCH STATUS
            } else if (choice.equals("5")) {
                System.out.println("Enter status (planning, watching, completed): ");
                String addStatus = scanner.nextLine().trim().toLowerCase();


                if (!addStatus.equals("planning") && !addStatus.equals("watching")
                        && !addStatus.equals("completed")) {
                    System.out.println(
                            "Invalid status. Please enter planning, watching, or completed.");
                    continue;
                }
                System.out.println("\nFILTER BY WATCH STATUS (planning, watching, completed)——\n");
                List<UserTVShowTracker> filterByStatus =
                        trackerDao.findAllByStatus(userId, addStatus);
                if (filterByStatus.isEmpty()) {
                    trackerDao.findAllWatchingAlphabetically(userId);
                } else {
                    for (UserTVShowTracker tracker : filterByStatus) {
                        TVShow show = tvShowDao.findTVShowById(tracker.getTvShowId());
                        System.out.println("Title: " + show.getTitle());
                        System.out.println("Progress: " + tracker.getProgress() + "/"
                                + show.getTotalEpisodes());
                        System.out.println("Status: " + tracker.getStatus());
                        System.out.println("Rating: " + tracker.getRating());
                        System.out.println();
                    }
                }
            }

            // MARK: 6—EXIT
            else if (choice.equals("6")) {
                break;
            } else {
                System.out.println("Wrong entry, please try again.");
            }



            // look up show in TVShow table——use tltle user provided

            // create/update UserTVShowTracker entry for this user and show
        }
    }
}
