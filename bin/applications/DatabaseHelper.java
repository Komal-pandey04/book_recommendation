package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {

    private static final String URL = "jdbc:sqlite:C:/Users/LENOVO/Desktop/BookLogin.db"; // Your SQLite DB file path
    private static Connection connection = null;

    // Method to establish a connection to the database
    public static Connection connect() {
        if (connection != null) {
            return connection;
        }
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        return connection;
    }

    // Method to insert a new user into the database (for Sign Up)
    public static boolean insertUser(String name, String username, String phone, String email, String password) {
        String query = "INSERT INTO users (name, username, phone, email, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, username);
            pstmt.setString(3, phone);
            pstmt.setString(4, email);
            pstmt.setString(5, password);
            pstmt.executeUpdate();
            return true; // Sign up successful
        } catch (SQLException e) {
            System.out.println("Error during user insertion: " + e.getMessage());
            return false; // Error during sign up
        }
    }

    // Method to check if the username/email and password are correct (for Log In)
    public static boolean checkLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true; // Login successful
            }
        } catch (SQLException e) {
            System.out.println("Error during login check: " + e.getMessage());
        }
        return false; // Login failed
    }
}
