# Progress Tracker - Java Backend Capstone Project

A console-based Java application for tracking movies and TV shows. Users can organize their watchlist by status ("Plan to Watch," "Watching," "Completed"), update progress, and rate completed shows.

---

## 🎯 Project Overview

This capstone demonstrates entry-level Java backend skills:

- **Object-Oriented Programming** with encapsulated models
- **MySQL Database Design** with normalized tables
- **JDBC & DAO Pattern** for clean data access
- **Custom Exception Handling** for robust error management
- **Maven Project Management** for dependencies
- **Git Version Control**
- **Console Interface** with user-friendly menus

---

## ✅ Requirements Met

### Core Requirements

- Console-based menu interface
- User authentication (login with username/password)
- Personal watchlist tracking (plan to watch/watching/completed)
- User-specific data access and security
- MySQL database with normalized schema
- JDBC & DAO pattern implementation
- 2 custom exceptions (AuthenticationException, TrackerNotFoundException)
- 10 sample shows seeded in the database
- Git version control ready

### Extensions Implemented

- Maven project structure
- User rating system (1-10 scale)
- Account creation functionality
- Sort/filter by status, rating, and title
- Show statistics (average rating, user counts)
- Admin account support

---

## 💾 Database Design

### ER Diagram Overview

```
USER (1) ←→ (N) USER_TVSHOW_TRACKER (N) ←→ (1) TVSHOW
```

### Tables

1. **User** - Stores user accounts and authentication

   - `id` (PK), `username`, `password`, `is_admin`

2. **TVShow** - Catalog of shows

   - `id` (PK), `title`, `total_episodes`

3. **UserTVShowTracker** - Tracks user's progress and ratings
   - `id` (PK), `user_id` (FK), `tv_show_id` (FK)
   - `progress`, `status` (ENUM), `rating`

### Sample Data Included

- 10 popular TV shows (Breaking Bad, The Office, etc.)
- 2 test users with sample progress and ratings

---

## 🚀 Setup Instructions

### Prerequisites

- Java 11+
- MySQL 8.0+
- Maven 3.6+
- Git

### 1. Clone Repository

```bash
git clone <your-repo-url>
cd capstone-project
```

### 2. Database Setup

```bash
mysql -u root -p < progress_tracker.sql
```

### 3. Configure Database Connection

Edit `src/main/resources/config.properties`:

```properties
url=jdbc:mysql://localhost:3306/progress_tracker?serverTimezone=EST5EDT
username=your_mysql_username
password=your_mysql_password
```

### 4. Build and Run

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.example.progress.App"
```

---

## 🖥️ Runtime Instructions

### Starting the Application

- Run with Maven: `mvn exec:java`
- Follow console prompts for login, account creation, and menu navigation

### Main Menu Example

```
===== Your Personal Watchlist =====
1. Login
2. Create Account
3. Exit
```

After login:

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

---

## 📊 Feature Walkthrough

- **Add to Watchlist**: Add new shows and set initial progress/status
- **Rate a Show**: Rate completed or in-progress shows (1-10)
- **Update Status**: Change status between planning, watching, completed
- **Update Rating**: Edit ratings for tracked shows
- **Delete Show Entry**: Remove a show from your tracker
- **View All Shows**: Browse all available shows
- **Search by Title**: Find shows by name
- **Sort/Filter**: Organize your watchlist by title, rating, or status
- **Admin Features**: Add/edit/delete shows (if logged in as admin)

---

## 🧪 Testing Data

**Test Accounts:**

- Username: `testaccount`, Password: `password123`
- Username: `andrewsales`, Password: `rootroot123`

**Sample Shows:**

- Love Island, Breaking Bad, The Office, Game of Thrones, Friends, etc.

---

## 🔧 Custom Exceptions

- **AuthenticationException**: Thrown for invalid login attempts
- **TrackerNotFoundException**: Thrown when accessing non-existent tracker data

---

## 🛠️ Technical Highlights

- **DAO Pattern**: Interface-based data access
- **PreparedStatements**: Prevent SQL injection
- **ConnectionManager**: Singleton for DB connections
- **Object-Oriented Design**: Encapsulated models
- **Normalized Database**: Foreign keys, ENUMs, constraints

---

## 📋 Git Workflow

```bash
git checkout -b feature/add-rating
git add .
git commit -m "Add user rating feature"
git push origin feature/add-rating
# Create pull request on GitHub
```

---

## 🚀 Future Enhancements

- REST API (Spring Boot)
- Web frontend (React/Angular)
- JWT authentication
- JUnit test coverage
- Docker deployment

---

## 🎓 Learning Objectives Achieved

- ✓ Object-oriented programming
- ✓ Exception handling
- ✓ JDBC database connectivity
- ✓ DAO and Singleton patterns
- ✓ Maven project management
- ✓ SQL schema design and normalization

---

**Progress Tracker** - Demonstrating Java backend development skills for entry-level positions. 🚀
