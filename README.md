# College Attendance Management System

## Project Description
The **College Attendance Management System** is a lightweight, console-based application designed to streamline the process of tracking student attendance. Built using **Core Java** and **JDBC** (Java Database Connectivity) with a **MySQL** database, this system provides a simple yet effective way for administrators or faculty to manage student records and daily attendance.

## Key Features
*   **Add Students**: Register new students with their Name and unique Roll Number.
*   **Mark Attendance**: Record daily attendance (Present/Absent/Leave) for students.
*   **View Attendance**: Retrieve and display all attendance records sorted by date.
*   **Update Attendance**: Modify existing attendance status in case of errors.
*   **Delete Records**: Remove incorrect or unnecessary attendance entries.
*   **Data Persistence**: All data is securely stored in a MySQL database, ensuring it remains available after the application closes.

## Technology Stack
*   **Language**: Java (JDK 8+)
*   **Database**: MySQL
*   **Connectivity**: JDBC (MySQL Connector/J)
*   **Tools**: Command Line Interface (CLI)

## How to Run
1.  **Setup Database**: Run the `setup_db.bat` script (requires MySQL root password).
2.  **Verify Driver**: Ensure `mysql-connector-j-x.x.x.jar` is in the `lib/` folder.
3.  **Start Application**: Double-click `run.bat` to compile and launch the system.

## Project Structure
*   `src/AttendanceManager.java`: Main application logic.
*   `database_setup.sql`: SQL script to create tables and sample data.
*   `lib/`: Contains the MySQL JDBC driver.
*   `bin/`: Contains compiled Java `.class` files.
