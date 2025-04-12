/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author CompEx-23
 */
public class User {

    private final String username;
    private final String email;
    private final String profilePic;
public User(String username, String email, String profilePic) {
this.username = username;
this.email = email;
this.profilePic = profilePic;
}
public String getUsername() { return username; }
public String getEmail() { return email; }
public String getProfilePic() { return profilePic; }
}
