/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author CompEx-23
 */
import javax.swing.*;
import java.awt.*;
public class UserDashboard extends JFrame {
public UserDashboard(User user) {
setTitle("User Profile");
setSize(400, 550);
setDefaultCloseOperation(EXIT_ON_CLOSE);
setLocationRelativeTo(null);
setLayout(new BorderLayout());
JLabel profilePic = new JLabel(new ImageIcon(user.getProfilePic()));
profilePic.setHorizontalAlignment(SwingConstants.CENTER);
add(profilePic, BorderLayout.NORTH);
JPanel info = new JPanel(new GridLayout(3, 1));
info.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
info.add(new JLabel("Username: " + user.getUsername()));
info.add(new JLabel("Email: " + user.getEmail()));
add(info, BorderLayout.CENTER);
JButton logoutBtn = new JButton("Logout");
logoutBtn.addActionListener(e -> {
new LoginSystem();
dispose();
});
add(logoutBtn, BorderLayout.SOUTH);
setVisible(true);
}
}