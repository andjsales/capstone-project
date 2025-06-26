// This will hold your main() method and your console menu logic

package com.example.progress;

import com.example.progress.dao.UserDao;
import com.example.progress.dao.impl.UserDaoImpl;
import com.example.progress.model.User;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import com.example.progress.connection.ConnectionManager;


public class App {

    public static void main(String[] args)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        try {
            Connection conn = ConnectionManager.getConnection();
            System.out.println("\nConnected to the database!");
        } catch (SQLException e) {
            System.out.println("\nFailed to connect to the database.");
            e.printStackTrace();
        } ;


        Scanner scanner = new Scanner(System.in);
        UserDao userDao = new UserDaoImpl(); // use DAO to check login
        User loggedInUser = null; // store logged-in user info

        System.out.println("\n===== Your Personal Watchlist =====");

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Exit");
            System.out.print("\nChoose an option: \n");

            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                // === Ask for username and password ===
                System.out.print("\nUsername: ");
                String username = scanner.nextLine();

                System.out.print("Password: ");
                String password = scanner.nextLine();

                // === Check credentials via DAO ===
                loggedInUser = userDao.findByUsernameAndPassword(username, password);

                if (loggedInUser != null) {
                    System.out.println("\n-----------------" + "\n\nWELCOME, "
                            + loggedInUser.getUsername() + "!");
                    break; // exit login loop and continue
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

        // You are now logged in!
        // Next step: show menu using loggedInUser.getId()
        // loggedInUser.getId();
        System.out.println("\nUser ID——" + loggedInUser.getId() + "\n");
    }
}
