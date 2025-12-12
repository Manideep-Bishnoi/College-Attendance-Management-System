
import java.sql.*;
import java.util.Scanner;

public class AttendanceManager {
    // Database Credentials
    private static final String URL = "jdbc:mysql://localhost:3306/attendance_system";
    private static final String USER = "root"; // CHANGE THIS if needed
    private static final String PASSWORD = "manideep@2027"; // CHANGE THIS if needed

    public static void main(String[] args) {
        try {
            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found! Include the jar in your classpath.");
            e.printStackTrace();
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to Database Successfull!");

            while (true) {
                System.out.println("\n--- Attendance Management System ---");
                System.out.println("1. Add Student");
                System.out.println("2. Mark Attendance");
                System.out.println("3. View Attendance");
                System.out.println("4. Update Attendance");
                System.out.println("5. Delete Attendance Record");
                System.out.println("6. Exit");
                System.out.print("Enter choice: ");

                int choice = -1;
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                } else {
                    scanner.nextLine(); // clean buffer
                }

                switch (choice) {
                    case 1:
                        addStudent(conn, scanner);
                        break;
                    case 2:
                        markAttendance(conn, scanner);
                        break;
                    case 3:
                        viewAttendance(conn);
                        break;
                    case 4:
                        updateAttendance(conn, scanner);
                        break;
                    case 5:
                        deleteAttendance(conn, scanner);
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    private static void addStudent(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Roll Number: ");
        String roll = scanner.nextLine();

        String sql = "INSERT INTO students (name, roll_number) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, roll);
            pstmt.executeUpdate();
            System.out.println("Student added successfully.");
        }
    }

    private static void markAttendance(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Roll Number: ");
        String roll = scanner.nextLine();

        // Get Student ID first
        int studentId = getStudentIdByRoll(conn, roll);
        if (studentId == -1) {
            System.out.println("Student not found!");
            return;
        }

        System.out.print("Enter Date (YYYY-MM-DD) [Leave empty for today]: ");
        String dateStr = scanner.nextLine();
        if (dateStr.isEmpty()) {
            dateStr = new java.sql.Date(System.currentTimeMillis()).toString();
        }

        System.out.print("Enter Status (Present/Absent/Leave): ");
        String status = scanner.nextLine();

        String sql = "INSERT INTO attendance (student_id, attendance_date, status) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setDate(2, Date.valueOf(dateStr));
            pstmt.setString(3, status);
            pstmt.executeUpdate();
            System.out.println("Attendance marked successfully.");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Attendance already marked for this date. Use Update instead.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date format.");
        }
    }

    private static void viewAttendance(Connection conn) throws SQLException {
        String sql = "SELECT a.id, s.name, s.roll_number, a.attendance_date, a.status " +
                "FROM attendance a JOIN students s ON a.student_id = s.id " +
                "ORDER BY a.attendance_date DESC";
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-5s %-15s %-10s %-12s %-10s\n", "ID", "Name", "Roll No", "Date", "Status");
            System.out.println("------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-5d %-15s %-10s %-12s %-10s\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("roll_number"),
                        rs.getDate("attendance_date"),
                        rs.getString("status"));
            }
        }
    }

    private static void updateAttendance(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Attendance Record ID to Update (from View menu): ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter New Status (Present/Absent/Leave): ");
        String status = scanner.nextLine();

        String sql = "UPDATE attendance SET status = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0)
                System.out.println("Attendance updated.");
            else
                System.out.println("Record not found.");
        }
    }

    private static void deleteAttendance(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Attendance Record ID to Delete (from View menu): ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String sql = "DELETE FROM attendance WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0)
                System.out.println("Attendance deleted.");
            else
                System.out.println("Record not found.");
        }
    }

    private static int getStudentIdByRoll(Connection conn, String roll) throws SQLException {
        String sql = "SELECT id FROM students WHERE roll_number = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, roll);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }
}
