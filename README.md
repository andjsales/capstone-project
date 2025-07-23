# TV-Show Progress Tracker

This is a console-based Java application for tracking TV shows. Users are able to organize their watchlist by status ("Plan to Watch," "Watching," "Completed"), update progress, and rate completed shows.

## Project Overview

- Object-Oriented Programming with encapsulated models
- MySQL Database Design 
- JDBC & DAO Pattern for clean data access
- Custom Exception Handling 
- Maven Project Management for dependencies
- Git Version Control
- Console Interface

## Requirements Met

### Core Requirements

- Console-based menu interface
- User authentication (logging in with username/password)
- Personal tracking (plan to watch/watching/completed)
- User-specific data access and security
- MySQL database
- JDBC & DAO pattern 
- 2 custom exceptions
- 10 sample shows seeded in the database
- Git version control ready

### Tables

1. **User** - Stores user accounts and authentication

   - `id` (PK), `username`, `password`, `is_admin`

2. **TVShow** - Catalog of shows

   - `id` (PK), `title`, `total_episodes`

3. **UserTVShowTracker** - Tracks user's progress and ratings
   - `id` (PK), `user_id` (FK), `tv_show_id` (FK)
   - `progress`, `status` (ENUM), `rating`

## Setup

### Prerequisites

- Java 11+
- MySQL 8.0+
- Maven 3.6+
- Git

### 1. Copy link and clone the repository

```bash
git clone <link>
cd capstone-project
```

### 2. Setup the database

```bash
mysql -u root -p < progress_tracker.sql
```

### 3. Configure the Database Connection

Edit this file: config.properties
`src/main/resources/config.properties`

```properties
url=jdbc:mysql://localhost:3306/progress_tracker?serverTimezone=EST5EDT
username=your_mysql_username
password=your_mysql_password
```

### 4. Compile and Run

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.example.progress.App"
```

### Starting the Application

- `mvn exec:java`

### Main Menu Demo

```
===== Your Personal Watchlist =====
1. Login
2. Create Account
3. Exit
```

(After logging in)

```
0 - Go back
1 - Exit
2 - Add to watchlist
3 - Rate a show
4 - Update status
5 - Update rating
6 - Delete show entry
7 - View all available shows
8 - Search for a show by title
9 - Sort alphabetically
10 - Sort by rating
11 - Sort by status
```

## Features

- **Add to Watchlist**: Add new shows and set initial progress/status
- **Rate a Show**: Rate completed or in-progress shows (1-10)
- **Update Status**: Change status between planning, watching, completed
- **Update Rating**: Edit ratings for tracked shows
- **Delete Show Entry**: Remove a show from your tracker
- **View All Shows**: Browse all available shows
- **Search by Title**: Find shows by name
- **Sort/Filter**: Organize your watchlist by title, rating, or status
- **Admin Features**: Add/edit/delete shows (if logged in as admin)

## Testing Data

**Available Test Accounts:**

- Username: `testaccount`, Password: `password123`
- Username: `andrewsales`, Password: `rootroot123`

## Custom Exceptions

- **AuthenticationException**: Thrown for invalid login attempts
- **TrackerNotFoundException**: Thrown when accessing non-existent tracker data

## Highlights

- **DAO Pattern**: Interface-based data access
- **PreparedStatements**: Prevent SQL injection
- **ConnectionManager**: Singleton for DB connections
- **Object-Oriented Design**: Encapsulated models
- **Normalized Database**: Foreign keys, ENUMs, constraints

