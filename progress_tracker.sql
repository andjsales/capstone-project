-- if database 'progress_tracker' exists——select
CREATE DATABASE IF NOT EXISTS progress_tracker;
USE progress_tracker;


-- each user has a unique——ID + username + password
CREATE TABLE User (
    id INT AUTO_INCREMENT PRIMARY KEY, -- AUTO_INCREMENT——ID increases automatically
    username VARCHAR(50) UNIQUE NOT NULL, -- UNIQUE——ensures no duplicate usernames
    password VARCHAR(100) NOT NULL
);


-- each show has——name + number of episodes
-- auto-generate the ID
CREATE TABLE TVShow (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    total_episodes INT NOT NULL
);


-- junction table——link users + TV shows
-- track——watching + how far along they are + their rating
CREATE TABLE UserTVShowTracker (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    tv_show_id INT,
    progress INT DEFAULT 0,
    status ENUM('Plan to Watch', 'Watching', 'Completed') NOT NULL, --ENUM——restricts status values
    rating INT CHECK (rating BETWEEN 1 AND 10), -- CHECK——ensures ratings are between 1-10
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE, -- ON DELETE CASCADE——deleting a user or show removes related tracker rows
    FOREIGN KEY (tv_show_id) REFERENCES TVShow(id) ON DELETE CASCADE
);

INSERT INTO User (username, password) VALUES -- sample user data
('amberglory', 'rootroot123'),
('andrewsales', 'password123');

INSERT INTO TVShow (title, total_episodes) VALUES -- sample TV shows data
('Love Island', 123),
('Regular Show', 244),
('Real Houswives of Salt Lake City', 51);

INSERT INTO UserTVShowTracker (user_id, tv_show_id, progress, status, rating) VALUES -- sample tracking info data
(1, 1, 123, 'Completed', 5),
(1, 2, 244, 'Completed', 10),
(1, 3, 51, 'Completed', 7),
(2, 1, 51, 'Watching', NULL),
(2, 2, 244, 'Completed', 10),
(2, 3, 0, 'Plan to Watch', NULL);
