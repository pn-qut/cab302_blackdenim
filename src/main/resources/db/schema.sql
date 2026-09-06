-- Tables for the habit tracker, based on the database design doc.
-- Run by DatabaseManager.initializeSchema() on startup.

-- Only username and password, to match User.java and IUserDAO as currently written.
-- The design doc's extra USER columns (name, email, profile_picture, created_at)
-- can be added once the User class carries them.
CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS habits (
    habit_id INTEGER PRIMARY KEY AUTOINCREMENT,
    habit_name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    category VARCHAR(50) NOT NULL,
    default_target INTEGER,
    frequency VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_habits (
    user_habit_id INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR(50) NOT NULL REFERENCES users(username),
    habit_id INTEGER NOT NULL REFERENCES habits(habit_id),
    start_date DATE NOT NULL,
    target INTEGER,
    is_active BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS habit_logs (
    log_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_habit_id INTEGER NOT NULL REFERENCES user_habits(user_habit_id),
    log_date DATE NOT NULL,
    completed BOOLEAN NOT NULL,
    value INTEGER
);

CREATE TABLE IF NOT EXISTS reminders (
    reminder_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_habit_id INTEGER NOT NULL REFERENCES user_habits(user_habit_id),
    reminder_time TIME NOT NULL,
    reminder_days VARCHAR(50) NOT NULL,
    is_enabled BOOLEAN NOT NULL
);
