CREATE DATABASE IF NOT EXISTS attendance_system;
USE attendance_system;

CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    roll_number VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    attendance_date DATE,
    status ENUM('Present', 'Absent', 'Leave') DEFAULT 'Absent',
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    UNIQUE KEY unique_attendance (student_id, attendance_date)
);

INSERT INTO students (name, roll_number) VALUES 
('Alice', '101'),
('Bob', '102'),
('Charlie', '103');

INSERT INTO attendance (student_id, attendance_date, status) VALUES 
(1, CURDATE(), 'Present'),
(2, CURDATE(), 'Absent');
