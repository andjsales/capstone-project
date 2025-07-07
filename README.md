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

## Example Login

Username: testaccount
Password: password123

## Setup Instructions

1. **Install MySQL** and create the database:

   ```bash
   mysql -u root -p < progress_tracker.sql
   ```

2. **Check config properties:**
   Make sure `config.properties` file matches your MySQL login:

   ```properties
   url=jdbc:mysql://localhost:3306/progress_tracker?serverTimezone=EST5EDT
   username=
   password=
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
