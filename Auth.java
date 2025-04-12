/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author CompEx-23
 */
import java.sql.*;

public class Auth {

    public static User login(String email, String password) {
        try (Connection conn = DBConnection.getConnection()) {
            // Use prepared statement to prevent SQL injection
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Assuming passwords are hashed in the database
                String storedPassword = rs.getString("password");
                if (verifyPassword(password, storedPassword)) {
                    return new User(
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("profile_pic")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error logging in: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
        return null;
    }

    // Method to verify password against a hashed version
    private static boolean verifyPassword(String inputPassword, String storedHash) {
        // For demonstration purposes, assume a simple hashing method
        // In real applications, use a strong hashing algorithm like bcrypt
        String hashedInput = hashPassword(inputPassword);
        return hashedInput.equals(storedHash);
    }

    // Simple password hashing method (replace with a more secure method in production)
    private static String hashPassword(String password) {
        // For demonstration purposes only; use a stronger hashing algorithm like bcrypt in real applications
        return String.valueOf(password.hashCode());
    }
}

