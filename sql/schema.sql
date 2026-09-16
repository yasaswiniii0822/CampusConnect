-- CampusConnect database schema
-- Run this once in MySQL before starting the app:
--   mysql -u root -p < schema.sql

DROP DATABASE IF EXISTS campusconnect;
CREATE DATABASE campusconnect CHARACTER SET utf8mb4;
USE campusconnect;

CREATE TABLE users (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(120)  NOT NULL,
    email           VARCHAR(180)  NOT NULL UNIQUE,
    password_hash   VARCHAR(255)  NOT NULL,
    password_salt   VARCHAR(64)   NOT NULL,
    college         VARCHAR(180)  NOT NULL,
    department      VARCHAR(120),
    year            VARCHAR(20),
    bio             TEXT,
    avatar_color    VARCHAR(20)   DEFAULT '#c1502f',
    onboarded       TINYINT(1)    DEFAULT 0,
    created_at      TIMESTAMP     DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE skills (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE user_skills (
    user_id     INT NOT NULL,
    skill_id    INT NOT NULL,
    PRIMARY KEY (user_id, skill_id),
    FOREIGN KEY (user_id)  REFERENCES users(id)  ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES skills(id) ON DELETE CASCADE
);

CREATE TABLE interests (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(80) NOT NULL UNIQUE,
    category    VARCHAR(40) NOT NULL
);

CREATE TABLE user_interests (
    user_id     INT NOT NULL,
    interest_id INT NOT NULL,
    PRIMARY KEY (user_id, interest_id),
    FOREIGN KEY (user_id)     REFERENCES users(id)     ON DELETE CASCADE,
    FOREIGN KEY (interest_id) REFERENCES interests(id) ON DELETE CASCADE
);

CREATE TABLE connections (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    requester_id    INT NOT NULL,
    receiver_id     INT NOT NULL,
    status          ENUM('pending','connected') DEFAULT 'pending',
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (requester_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id)  REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY uniq_pair (requester_id, receiver_id)
);

CREATE TABLE posts (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT NOT NULL,
    post_type   VARCHAR(30) DEFAULT 'general',
    content     TEXT NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE events (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    title               VARCHAR(160) NOT NULL,
    description         TEXT,
    organizer           VARCHAR(160),
    category            VARCHAR(60),
    event_date          DATE,
    event_time          VARCHAR(20),
    venue               VARCHAR(160),
    capacity            INT DEFAULT 0,
    created_by          INT,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE event_registrations (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    event_id        INT NOT NULL,
    user_id         INT NOT NULL,
    registered_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id)  REFERENCES users(id)  ON DELETE CASCADE,
    UNIQUE KEY uniq_reg (event_id, user_id)
);

-- Seed data: interest catalogue (matches the onboarding categories)
INSERT INTO interests (name, category) VALUES
('AI/ML','Technology'), ('Web Development','Technology'), ('App Development','Technology'),
('Cybersecurity','Technology'), ('Data Science','Technology'), ('IoT','Technology'), ('Robotics','Technology'),
('Design','Creative'), ('Photography','Creative'), ('Video','Creative'), ('Writing','Creative'),
('Music','Creative'), ('Dance','Creative'),
('Entrepreneurship','Professional'), ('Business','Professional'), ('Marketing','Professional'),
('Finance','Professional'), ('Public Speaking','Professional'),
('Sports','Other'), ('Gaming','Other'), ('Volunteering','Other'), ('Research','Other'), ('Academics','Other');

-- Seed data: a starter skill list students can attach to their profile
INSERT INTO skills (name) VALUES
('Python'),('Java'),('JavaScript'),('React'),('Node.js'),('Machine Learning'),
('UI/UX'),('Figma'),('SQL'),('C++'),('Public Speaking'),('Video Editing'),
('Content Writing'),('Flutter'),('Data Analysis');

-- Seed data: a couple of demo events so the Events page isn't empty on first run
INSERT INTO events (title, description, organizer, category, event_date, event_time, venue, capacity) VALUES
('AI/ML Workshop: Building Your First Model', 'A hands-on Saturday workshop covering the basics of training and deploying a simple ML model.', 'AI Club', 'Technical', CURDATE() + INTERVAL 5 DAY, '2:00 PM', 'Engineering Block, Lab 3', 80),
('Campus Photography Walk', 'An informal photo walk around campus, open to all skill levels.', 'Photography Club', 'Creative', CURDATE() + INTERVAL 9 DAY, '4:30 PM', 'Main Gate', 40),
('Startup Pitch Night', 'Student founders pitch their ideas to a panel of alumni and faculty.', 'Entrepreneurship Cell', 'Professional', CURDATE() + INTERVAL 14 DAY, '6:00 PM', 'Auditorium', 150);
