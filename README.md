# Progress Tracker

## Description

This is a Java console application built to allow users to manage and track their movie/show watchlists. Users can log in, add shows, update their progress, filter by watch status, and rate completed shows. This app uses a MySQL database and follows the DAO pattern for clean, modular code.

## Features

- User login with username/password
- View available TV shows
- Track individual progress on episodes
- Update watch status (Plan to Watch, Watching, Completed)
- Rate completed shows
- Built-in database schema with sample data

## Tech Stack

- Java 11
- JDBC
- MySQL
- Maven
- DAO Pattern

## Database Schema

**Database:** `progress_tracker`  
Includes three main tables:

1. **User**: stores login credentials
2. **TVShow**: catalog of shows with total episodes
3. **UserTVShowTracker**: tracks users’ show progress, watch status, and ratings

## Setup Instructions

1. **Install MySQL** and create the database:

   ```bash
   mysql -u root -p < progress_tracker.sql
   ```

2. **Check config properties:**
   Ensure your `config.properties` file matches your MySQL login:

   ```properties
   url=jdbc:mysql://localhost:3306/progress_tracker?serverTimezone=EST5EDT
   username=your_mysql_username
   password=your_mysql_password
   ```

3. **Build and Run**
   ```bash
   mvn clean compile
   mvn exec:java -Dexec.mainClass="com.example.progress.App"
   ```

## Lessons Learned

- Implementing DAO design patterns for modular and testable code
- Using Maven to manage dependencies and build processes
- Writing and executing SQL scripts for schema design and seed data
- Managing database connections securely through config files
- Applying object-oriented design in real-world CRUD apps

## Future Improvements

- Add support for tracking books or music
- Implement user registration
- Create a GUI (JavaFX or Swing)
- Secure passwords using hashing algorithms
- add a Movies entity

## Methods to add

- addTVShowToList()
- updateTVShowWatchStatus()
- updateTVShowRating()
- updateTVShowProgress()
- fix findAllShows() method

## console menu——order of operations

1. login/exit——choose an option (1 or 2)
2. type in username——enter
3. type in password——enter
4. WELCOME
5. 1•view all shows——2•view all currently watching——choose an option
6. if 1——return all show titles——progress——total eps
7. if 2——return all currently watching——title——progress——status——rating

## next steps

8. method to add shows——ask for title——progress——status——rating
9.
