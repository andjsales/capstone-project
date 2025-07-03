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
import java.sql.PreparedStatement;
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

        // MARK: CONNECT DATABASE—
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
        // MARK: LOGIN—EXIT—CREATE ACCOUNT

        System.out.println("\n===== Your Personal Watchlist =====");

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Create Account");
            System.out.println("3. Exit");
            System.out.print("\nEnter a number: \n");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.print("\nUsername: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();

                // MARK: check credentials using DAO—
                loggedInUser = userDao.findByUsernameAndPassword(username, password);

                if (loggedInUser != null) {
                    System.out.println("\n—————————————————————————————————" + "\n\nWELCOME, "
                            + loggedInUser.getUsername() + "!");
                    System.out.println("(User ID: " + loggedInUser.getId() + ")");

                    break; // exit login loop ——> continue

                    // MARK: You are now logged in!—

                } else {
                    System.out.println("\nIncorrect login——please try again.");
                }

            } else if (choice.equals("2")) {
                System.out.print("\nChoose a username: ");
                String newUsername = scanner.nextLine().trim();
                System.out.print("Choose a password: ");
                String newPassword = scanner.nextLine().trim();

                User existingUser = userDao.findByUsernameAndPassword(newUsername, newPassword);
                if (existingUser != null) {
                    System.out.println("Username already taken. Please try a different one.");
                } else {
                    String sql = "INSERT INTO User (username, password) VALUES (?, ?)";
                    try (Connection conn = ConnectionManager.getConnection();
                            PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, newUsername);
                        stmt.setString(2, newPassword);
                        stmt.executeUpdate();
                        System.out.println("\nAccount created successfully! You can now log in.");
                    } catch (Exception e) {
                        System.out.println("Error creating account.");
                        e.printStackTrace();
                    }
                }

            } else if (choice.equals("3")) {
                System.out.println("Exiting program. Goodbye!");
                System.exit(0);
            } else {
                System.out.println("\nWrong entry. Please choose 1, 2 or 3.");
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


            // MARK: MENU OPTIONS—
            System.out.println("\n...\n");
            System.out.println("1. View all saved shows");
            System.out.println("2. Exit");
            System.out.println("3. Add to watchlist");
            System.out.println("4. Search for a show by title");
            System.out.println("5. Sort currently watching alphabetically");
            System.out.println("6. Sort currently watching by rating");
            System.out.println("7. Filter by watch status");
            System.out.println("8. Rate a show");
            System.out.println("\n—————————————————————————————————");
            System.out.print("\nChoose an option: ");

            String choice = scanner.nextLine();

            // MARK: 1—VIEW ALL SHOWS
            if (choice.equals("1")) {
                System.out.println("\n1——VIEW ALL SAVED SHOWS\n");
                for (TVShow show : allShows) {
                    int progress = getUserProgressForShow(sortByTitle, show.getId());
                    System.out.println('"' + show.getTitle() + '"' + " — " + progress + "/"
                            + show.getTotalEpisodes());
                }

                // MARK: 2—EXIT
            } else if (choice.equals("2")) {
                break;

                // MARK: 3—ADD TO WATCHLIST
            } else if (choice.equals("3")) {
                System.out.println("\n3——ADD TO WATCHLIST\n");
                System.out.println("Enter title: ");
                String addTitle = scanner.nextLine();
                System.out.print("Enter total # of episodes: ");
                int addTotalEpisodes = Integer.parseInt(scanner.nextLine());
                int addProgress = 0;
                while (true) {
                System.out.println("Enter # of episodes watched: ");
                    String progressInput = scanner.nextLine().trim();
                    try {
                        addProgress = Integer.parseInt(progressInput);
                        if (addProgress >= 0 && addProgress <= addTotalEpisodes) {
                            break;
                        } else {
                            System.out.println(
                                    "Progress must be between 0 and " + addTotalEpisodes + ".");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number.");
                    }
                }
                System.out.println("Enter watching status (planning, watching, completed): ");
                String addStatus = scanner.nextLine().trim().toLowerCase();
                System.out.println("\nEnter rating (optional): ");
                Integer addRating = null;
                while (true) {
                    String ratingInput = scanner.nextLine().trim();
                    if (ratingInput.isEmpty()) {
                        addRating = null;
                        break;
                    }
                    try {
                        int ratingValue = Integer.parseInt(ratingInput);
                        if (ratingValue >= 1 && ratingValue <= 10) {
                            addRating = ratingValue;
                            break;
                        } else {
                            System.out.println("Rating must be between 1 and 10.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number or leave blank.");
                    }
                }
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

                // MARK: 4—SEARCH FOR TITLE
            } else if (choice.equals("4")) {
                System.out.print("Enter the title to search for: ");
                String searchTitle = scanner.nextLine().trim();
                TVShow foundShow = tvShowDao.findTVShowByTitle(searchTitle);
                if (foundShow != null) {
                    System.out.println("\nShow found:");
                    System.out.println("Title: " + foundShow.getTitle());
                    System.out.println("Total Episodes: " + foundShow.getTotalEpisodes());
                    int progress = getUserProgressForShow(sortByTitle, foundShow.getId());
                    System.out.println(
                            "Your Progress: " + progress + "/" + foundShow.getTotalEpisodes());
                } else {
                    System.out.println("No show found with that title.");
                }

                // MARK: 5—SORT ALPHABETICALLY
            } else if (choice.equals("5")) {
                if (sortByTitle.isEmpty()) {
                    System.out.println("\n5——ALL CURRENTLY WATCHING (Sorted alphabetically)\n");
                    System.out.println("\nYou are not currently watching any shows or movies.");
                } else {
                    System.out.println("\n5——ALL CURRENTLY WATCHING (Sorted alphabetically)\n");
                    for (UserTVShowTracker tracker : sortByTitle) {
                        TVShow show = tvShowDao.findTVShowById(tracker.getTvShowId());
                        System.out.println("Title: " + show.getTitle());
                        System.out.println("Progress: " + tracker.getProgress() + "/"
                                + show.getTotalEpisodes());
                        System.out.println("Status: " + tracker.getStatus());
                        System.out.println("Rating: " + tracker.getRating());
                    }
                }

                // MARK: 6—SORT BY RATING
            } else if (choice.equals("6")) {
                System.out.println("\n6——ALL CURRENTLY WATCHING (Sorted by rating)\n");
                if (sortByRating.isEmpty()) {
                    System.out.println("\nYou are not currently watching any shows or movies.");
                }
                for (UserTVShowTracker tracker : sortByRating) {
                    TVShow show = tvShowDao.findTVShowById(tracker.getTvShowId());
                    System.out.println("Title: " + show.getTitle());
                    System.out.println(
                            "Progress: " + tracker.getProgress() + "/" + show.getTotalEpisodes());
                    System.out.println("Status: " + tracker.getStatus());
                    System.out.println("Rating: " + tracker.getRating());
                    System.out.println();
                }

                // MARK: 7—FILTER BY WATCH STATUS
            } else if (choice.equals("7")) {
                System.out.println("Enter status (planning, watching, completed): ");
                String filterStatus = scanner.nextLine().trim().toLowerCase();
                if (!filterStatus.equals("planning") && !filterStatus.equals("watching")
                        && !filterStatus.equals("completed")) {
                    System.out.println(
                            "Invalid status. Please enter planning, watching, or completed.");
                    continue;
                }
                System.out.println("\nFILTER BY WATCH STATUS (" + filterStatus + ")——");
                List<UserTVShowTracker> filterByStatus =
                        trackerDao.findAllByStatus(userId, filterStatus);
                if (filterByStatus.isEmpty()) {
                    System.out.println("No shows found for that status.");
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
