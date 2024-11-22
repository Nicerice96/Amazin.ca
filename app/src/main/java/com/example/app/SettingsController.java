
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

    @GetMapping("/settingsEntry")
    public String settings() {
        return "settingsEntry";
    }

    @PostMapping("/changeUsername")
    public ResponseEntity<String> changeUsername(@RequestParam String oldUsername, @RequestParam String newUsername) {
        User user = userRepository.findByUsername(oldUsername);
        user.setUsername(newUsername);
        userRepository.save(user);
        return new ResponseEntity<>("Username updated successfully!", HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestParam String username, @RequestParam String currentPassword, @RequestParam String newPassword) {
        User user = userRepository.findByUsername(username);
        if (user.getPassword().equals(currentPassword)) {
            user.setPassword(newPassword);
            userRepository.save(user);
            return new ResponseEntity<>("Password updated successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Current password is incorrect.", HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/deleteAccount")
    public ResponseEntity<String> deleteAccount(@RequestParam String username, @RequestParam String password){
        User user = userRepository.findByUsername(username);
        if (user.getPassword().equals(password)) {
            userRepository.delete(user);
            return new ResponseEntity<>("Account has been deleted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Incorrect username or password, Account deletion aborted", HttpStatus.BAD_REQUEST);
        }
    }

}