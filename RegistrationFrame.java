import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.sql.*;

public class RegistrationFrame extends JFrame {

    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private File selectedImage;

    public RegistrationFrame() {
        setTitle("Register - MyApp");
        setSize(400, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Logo
        ImageIcon originalIcon = new ImageIcon("resources/logo.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Width x Height
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(resizedIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(logoLabel, BorderLayout.NORTH);

        // Form Panel
        JPanel form = new JPanel(new GridLayout(7, 1, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        usernameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();

        form.add(new JLabel("Username"));
        form.add(usernameField);
        form.add(new JLabel("Email"));
        form.add(emailField);
        form.add(new JLabel("Password"));
        form.add(passwordField);

        // Upload Button
        JButton uploadBtn = new JButton("Upload Profile Picture");
        uploadBtn.addActionListener(e -> uploadProfilePicture());
        form.add(uploadBtn);

        // Submit Button
        JButton submitBtn = new JButton("Register");
        submitBtn.addActionListener(e -> registerUser());
        form.add(submitBtn);

        add(form, BorderLayout.CENTER);
        setVisible(true);
    }

    private void uploadProfilePicture() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            selectedImage = chooser.getSelectedFile();
        }
    }

    private void registerUser() {
        try (Connection conn = DBConnection.getConnection()) {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            // Basic validation
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            String profilePath = null;
            if (selectedImage != null) {
                File dest = new File("profile_pics/" + selectedImage.getName());
                try {
                    Files.copy(selectedImage.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    profilePath = dest.getPath();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Failed to upload profile picture: " + e.getMessage());
                    return;
                }
            }

            // Use prepared statement to prevent SQL injection
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, email, password, profile_pic) VALUES (?, ?, ?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, email);
            // Password should be hashed before storing
            stmt.setString(3, hashPassword(password));
            stmt.setString(4, profilePath);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registration successful!");
            new LoginSystem();
            dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Registration failed: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + e.getMessage());
        }
    }

    // Simple password hashing method (replace with a more secure method in production)
    private String hashPassword(String password) {
        // For demonstration purposes only; use a stronger hashing algorithm like bcrypt in real applications
        return String.valueOf(password.hashCode());
    }
}