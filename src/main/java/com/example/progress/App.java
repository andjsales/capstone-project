package com.example.progress;

import com.example.progress.dao.UserDao;
import com.example.progress.dao.impl.UserDaoImpl;
import com.example.progress.model.TVShow;
import com.example.progress.model.User;
import com.example.progress.model.UserTVShowTracker;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
        }

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
                    System.out.println("Username taken. Pick another username.");
                } else {
                    String sql = "INSERT INTO User (username, password) VALUES (?, ?)";
                    try (Connection conn = ConnectionManager.getConnection();
                            PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, newUsername);
                        stmt.setString(2, newPassword);
                        stmt.executeUpdate();
                        System.out.println("\nAccount creation success! You can log in.");
                    } catch (Exception e) {
                        System.out.println("Problem creating account.");
                        e.printStackTrace();
                    }
                }

            } else if (choice.equals("3")) {
                System.out.println("bye byee!");
                System.exit(0);
            } else {
                System.out.println("\nInvalid entry. Please choose 1, 2 or 3.");
            }
        }
        while (true) {
            // ——show menu using loggedInUser.getId()
            loggedInUser.getId();

            // MARK: useful methods:
            TrackerDao trackerDao = new TrackerDaoImpl();
            TVShowDao tvShowDao = new TVShowDaoImpl();
            int userId = loggedInUser.getId();
            List<UserTVShowTracker> sortByTitle = trackerDao.findAllWatchingAlphabetically(userId);
            List<UserTVShowTracker> sortByRating = trackerDao.findAllOrderByRating(userId);
            List<TVShow> allShows = tvShowDao.findTVShowById();

            // MARK: MENU OPTIONS—
            System.out.println("\n• • •\n");
            System.out.println("0. Go back");
            System.out.println("1. Exit");
            System.out.println("");
            System.out.println("2. Add to watchlist");
            System.out.println("3. Rate a show");
            System.out.println("");
            System.out.println("4. View all available shows");
            System.out.println("5. Search for a show by title");
            System.out.println("6. Sort alphabetically");
            System.out.println("7. Sort by rating");
            System.out.println("8. Sort by status");
            System.out.println("\n—————————————————————————————————");
            System.out.print("\nChoose an option: ");

            String choice = scanner.nextLine();

            // MARK: 0—GO BACK
            if (choice.equals("0")) {
                System.out.println("Returning to login menu...");
                break; // This will exit the main menu and return to the login loop

                // MARK: 1—EXIT
            } else if (choice.equals("1")) {
                break;

                // MARK: 2—ADD TO WATCHLIST
            } else if (choice.equals("2")) {
                System.out.println("\n2——ADD TO WATCHLIST:\n");
                System.out.println("Enter title (or 0 to go back): ");
                String addTitle = scanner.nextLine();
                if (addTitle.equals("0"))
                    continue;
                System.out.print("Enter total # of episodes (or 0 to go back): ");
                String totalEpisodesInput = scanner.nextLine();
                if (totalEpisodesInput.equals("0"))
                    continue;
                int addTotalEpisodes = Integer.parseInt(totalEpisodesInput);
                int addProgress = 0;
                while (true) {
                    System.out.println("Enter # of episodes watched (or 0 to go back): ");
                    String progressInput = scanner.nextLine().trim();
                    if (progressInput.equals("0"))
                        continue;
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
                System.out.println(
                        "Enter watching status (planning, watching, completed) or 0 to go back: ");
                String addStatus = scanner.nextLine().trim().toLowerCase();
                if (addStatus.equals("0"))
                    continue;
                System.out.println("\nEnter rating (optional, or 0 to go back): ");
                Integer addRating = null;
                while (true) {
                    String ratingInput = scanner.nextLine().trim();
                    if (ratingInput.equals("0"))
                        continue;
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
                    continue;
                }
                TVShow showFromDb = tvShowDao.findTVShowByTitle(addTitle);
                if (showFromDb == null) {
                    System.out.println("Could not find new show in the database.");
                    continue;
                }
                trackerDao.addUserTVShowTracker(loggedInUser.getId(), showFromDb.getId(),
                        addProgress, addStatus, addRating);
                System.out.println("Show added successfully!");

                // MARK: 3—RATE A SHOW
            } else if (choice.equals("3")) {
                List<UserTVShowTracker> userTrackers =
                        trackerDao.findAllByStatus(userId, "watching");
                userTrackers.addAll(trackerDao.findAllByStatus(userId, "completed"));

                if (userTrackers.isEmpty()) {
                    System.out.println("No shows available to rate.");
                    continue;
                }

                System.out.println("\nYour shows:\n");
                for (int i = 0; i < userTrackers.size(); i++) {
                    TVShow show = tvShowDao.findTVShowById(userTrackers.get(i).getTvShowId());
                    System.out.println((i + 1) + ". " + show.getTitle() + " (Current rating: "
                            + (userTrackers.get(i).getRating() == null ? "none"
                                    : userTrackers.get(i).getRating())
                            + ")");
                }
                System.out.print(
                        "\nEnter the number of the show you want to rate (or 0 to go back): ");
                int showChoice = -1;
                String showChoiceInput = scanner.nextLine().trim();
                if (showChoiceInput.equals("0"))
                    continue;
                try {
                    showChoice = Integer.parseInt(showChoiceInput);
                    if (showChoice < 1 || showChoice > userTrackers.size()) {
                        System.out.println("Invalid selection.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                    continue;
                }
                UserTVShowTracker selectedTracker = userTrackers.get(showChoice - 1);

                Integer newRating = null;
                while (true) {
                    System.out.print(
                            "Enter new rating (1-10, or leave blank to remove rating, or 0 to go back): ");
                    String ratingInput = scanner.nextLine().trim();
                    if (ratingInput.equals("0")) {
                        newRating = null;
                        break;
                    }
                    if (ratingInput.isEmpty()) {
                        newRating = null;
                        break;
                    }
                    try {
                        int ratingValue = Integer.parseInt(ratingInput);
                        if (ratingValue >= 1 && ratingValue <= 10) {
                            newRating = ratingValue;
                            break;
                        } else {
                            System.out.println("Rating must be between 1 and 10.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number or leave blank.");
                    }
                }
                trackerDao.updateRating(loggedInUser.getId(), selectedTracker.getTvShowId(),
                        newRating);
                System.out.println("\nRating updated!");

                // MARK: 4—VIEW ALL SHOWS
            } else if (choice.equals("4")) {
                System.out.println("\n4——VIEWING ALL AVAILABLE SHOWS:\n");
                for (TVShow show : allShows) {
                    int progress = getUserProgressForShow(sortByTitle, show.getId());
                    System.out.println('"' + show.getTitle() + '"' + " — " + progress + "/"
                            + show.getTotalEpisodes());
                }

                // MARK: 8 — search for title

            } else if (choice.equals("8")) {
                System.out.print("\nTitle: ");
                String searchTitle = scanner.nextLine().trim();
                if (searchTitle.equals("0"))
                    continue;
                TVShow foundShow = tvShowDao.findTVShowByTitle(searchTitle);
                if (foundShow != null) {
                    System.out.println("\nSHOW FOUND: " + foundShow.getTitle());
                    System.out.println("Total Eps: " + foundShow.getTotalEpisodes());
                    int progress = getUserProgressForShow(sortByTitle, foundShow.getId());
                    System.out
                            .println("Progress: " + progress + "/" + foundShow.getTotalEpisodes());
                } else {
                    System.out.println("Show not added.");
                }

                // MARK: 9 — sort alphabetically
            } else if (choice.equals("9") || choice.equals("7")) {
                if (allShows.isEmpty()) {
                    System.out.println("\nNo shows added.");
                } else {
                    System.out.println("\n6——ALL SHOWS (Sorted alphabetically)\n");
                    System.out.println("\n9——SORT ALPHABETICALLY (All Shows):\n");
                    // sort all shows alphabetically by title
                    List<TVShow> showsCopy = new ArrayList<>(allShows);
                    showsCopy.sort((a, b) -> a.getTitle().compareToIgnoreCase(b.getTitle()));
                    // loop thru each show
                    for (TVShow show : showsCopy) {
                        // look for tracker object that matches each show's ID
                        UserTVShowTracker tracker = null;
                        for (UserTVShowTracker t : sortByTitle) {
                            if (t.getTvShowId() == show.getId()) {
                                tracker = t;
                                break;
                            }
                        }
                        // if tracker exists > get values
                        int progress = (tracker != null) ? tracker.getProgress() : 0;
                        String status = (tracker != null) ? tracker.getStatus() : "planning";
                        String rating = (tracker != null //
                                && tracker.getRating() != null) //
                                ? tracker.getRating().toString()
                                        : "n/a";
                        System.out.println("\"" + show.getTitle() + "\"");
                        System.out.println("Progress——" + progress + "/" + show.getTotalEpisodes());
                        System.out.println("Status——" + status);
                        System.out.println("Rating——" + rating);
                        System.out.println();
                    }
                }

                // MARK: 7—SORT BY RATING
            } else if (choice.equals("7")) {
                System.out.println("\n7——ALL CURRENTLY WATCHING (Sorted by rating):\n");

                if (allShows.isEmpty()) {
                    System.out.println("\nNo shows added.");
                } else {
                    System.out.println("\n7——ALL SHOWS (Sorted by rating)\n");
                    for (TVShow show : allShows) {
                        UserTVShowTracker tracker = null;
                        for (UserTVShowTracker t : sortByRating) {
                            if (t.getTvShowId() == show.getId()) {
                                tracker = t;
                                break;
                            }
                        }
                        int progress = (tracker != null) ? tracker.getProgress() : 0;
                        String status = (tracker != null) ? tracker.getStatus() : "planning";
                        String rating = (tracker != null && tracker.getRating() != null)
                                ? tracker.getRating().toString()
                                : "none";
                        System.out.println("Title: " + show.getTitle());
                        System.out.println("Progress: " + progress + "/" + show.getTotalEpisodes());
                        System.out.println("Status: " + status);
                        System.out.println("Rating: " + rating);
                        System.out.println();
                    }
                }

                // MARK: 8—SORT BY WATCH STATUS
            } else if (choice.equals("8")) {
                System.out
                        .println("Enter status (planning, watching, completed) or 0 to go back: ");
                String filterStatus = scanner.nextLine().trim().toLowerCase();
                if (filterStatus.equals("0"))
                    continue;

                if (!filterStatus.equals("planning") && !filterStatus.equals("watching")
                        && !filterStatus.equals("completed")) {
                    System.out.println(
                            "Invalid status. Please enter planning, watching, or completed.");
                    continue;
                }
                System.out.println("\nSORT BY STATUS (" + filterStatus + ")——");
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
            // look up show in TVShow table——use title user provided

            // create/update UserTVShowTracker entry for this user and show
        }
    }
}
