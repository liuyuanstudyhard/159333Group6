package com.coffee_backend.controller;


import com.coffee_backend.entity.User;
import com.coffee_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    // User registration interface
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest request) {
        Long userId = userService.registerUser(request.getUsername(), request.getPassword(),
                request.getName(), request.getEmail(),
                request.getPhone(), request.getGender(),
                request.getAddress());
        if (userId == -1){
            return ResponseEntity.ok("{\"message\": \"Account already exists, please do not re-register!\", \"userId\": " + userId + "}");
        }
        if (userId != null) {
            return ResponseEntity.ok("{\"message\": \"Registered successfully!\", \"userId\": " + userId + "}");
        } else {
            return ResponseEntity.status(400).body("{\"error\": \"User name already exists!\"}");
        }
    }

    // User login interface
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginRequest request) {
        User user = userService.loginUser(request.getUsername(), request.getPassword());
        if (user != null) {
            return ResponseEntity.ok("{\"message\": \"Login successful!\", \"userId\": " + user.getId() + "}");
        } else {
            return ResponseEntity.status(401).body("{\"error\": \"Incorrect username or password!\"}");
        }
    }

    // Interface for viewing user information
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    // Interface for modifying user information
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User request) {
        boolean success = userService.updateUser(id, request.getName(), request.getEmail(),
                request.getPhone(), request.getGender(), request.getAddress());
        if (success) {
            return ResponseEntity.ok("{\"message\": \"User information updated successfully!\"}");
        } else {
            return ResponseEntity.status(400).body("{\"error\": \"User information update failed!\"}");
        }
    }

    // The class used to receive registration requests
    public static class UserRegistrationRequest {
        private String username;
        private String password;
        private String name;
        private String email;
        private String phone;
        private String gender;
        private String address;

        // Getters and setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
    }

    // The class used to receive login requests
    public static class UserLoginRequest {
        private String username;
        private String password;

        // Getters and setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
