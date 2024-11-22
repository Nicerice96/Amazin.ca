
package com.example.app;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/settings")
public class SettingsController{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;

    /**
     * Default home of the Settings page
     * @return html as String
     */
    @GetMapping("/settingsEntry")
    public String settings() {
        return "settingsEntry";
    }

    /**
     * Change User's or Admin's username to a new username
     * @param oldUsername User's or Admin's old username
     * @param newUsername User's or Admin's new username
     * @return ResponseEntity with the user if change is successful, or HTTP status code
     */
    @PostMapping("/changeUsername")
    public ResponseEntity<String> changeUsername(@RequestParam String oldUsername, @RequestParam String newUsername) {
        User user = userRepository.findByUsername(oldUsername);
        Admin admin = adminRepository.findByUsername(oldUsername);
        // entity can be both user and admin, so ensure both get changed
        if (user != null) {
            user.setUsername(newUsername);
            userRepository.save(user);
        } else if (admin != null){
            admin.setUsername(newUsername);
            adminRepository.save(admin);
        } else {
            return new ResponseEntity<>("User or Admin not found.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Username updated successfully!", HttpStatus.OK);
    }

    /**
     * hange User's or Admin's password to a new password
     * @param username User's or Admin's username
     * @param currentPassword User's or Admin's current password
     * @param newPassword User's or Admin's new password
     * @return ResponseEntity with the user if change is successful, or HTTP status code
     */
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestParam String username, @RequestParam String currentPassword, @RequestParam String newPassword) {
        User user = userRepository.findByUsername(username);
        Admin admin = adminRepository.findByUsername(username);
        // entity can be both user and admin, so ensure both get changed
        if (user != null && user.getPassword().equals(currentPassword)) {
            user.setPassword(newPassword);
            userRepository.save(user);
        } else if (admin != null && admin.getPassword().equals(currentPassword)) {
            admin.setPassword(newPassword);
            adminRepository.save(admin);
        } else {
            return new ResponseEntity<>("Incorrect username or password.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Password updated successfully!", HttpStatus.OK);
    }

    /**
     * Delete User's or Admin's account
     * @param username User's or Admin's username
     * @param password User's or Admin's password
     * @return ResponseEntity with the user if deletion is successful, or HTTP status code
     */
    @DeleteMapping("/deleteAccount")
    public ResponseEntity<String> deleteAccount(@RequestParam String username, @RequestParam String password) {
        User user = userRepository.findByUsername(username);
        Admin admin = adminRepository.findByUsername(username);
        // entity can be both user and admin, so ensure both get changed
        if (user != null && user.getPassword().equals(password)) {
            userRepository.delete(user);
        } else if (admin != null && admin.getPassword().equals(password)) {
            adminRepository.delete(admin);
        } else {
            return new ResponseEntity<>("Incorrect username or password, account deletion aborted.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Account has been deleted.", HttpStatus.OK);
    }

}
