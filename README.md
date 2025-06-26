# Progress Tracker

A Java console application that helps users track their TV show watching progress. Built using the DAO pattern, JDBC, and a MySQL backend, this project demonstrates modular software development, relational database interaction, and Maven-based project structure.

## Features

- User login with username/password
- View available TV shows
- Track individual progress on episodes
- Update watch status (Plan to Watch, Watching, Completed)
- Rate completed shows
- Built-in database schema with sample data

## Tech Stack

- Java 11
- JDBC (Java Database Connectivity)
- MySQL
- Maven (project management)
- DAO Pattern (Data Access Object)

## Database Schema

**Database:** `progress_tracker`  
Includes three main tables:

1. **User**: stores login credentials
2. **TVShow**: catalog of shows with total episodes
3. **UserTVShowTracker**: tracks users’ show progress, watch status, and ratings

Sample data is auto-loaded via the [`progress_tracker.sql`](./progress_tracker.sql) script.

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
