package com.bilal.learning_platform.controller;

import com.bilal.learning_platform.dto.UserDto;
import com.bilal.learning_platform.model.User;
import com.bilal.learning_platform.payload.request.ChangePasswordRequest;
import com.bilal.learning_platform.payload.request.UserProfileRequest;
import com.bilal.learning_platform.payload.response.MessageResponse;
import com.bilal.learning_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UserProfileRequest userProfileRequest, Authentication authentication) {

        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("Error: User is not found."));

        user.setFname(userProfileRequest.getFname());
        user.setLname(userProfileRequest.getLname());
        user.setHeadline(userProfileRequest.getHeadline());
        user.setBio(userProfileRequest.getBio());
        user.setWebsite(userProfileRequest.getWebsite());
        user.setTwitter(userProfileRequest.getTwitter());

        userRepository.save(user);

        return ResponseEntity.ok(new UserDto(user));
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request, Authentication authentication) {

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Error: User is not found."));
        if (!encoder.matches(request.getOldPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Password is not matches.");
        }

        user.setPassword(encoder.encode(request.getPassword()));
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Successfully Updated!"));
    }

    @GetMapping("/loggedin")
    public ResponseEntity<?> getUserData(Authentication authentication) {
        System.out.println(authentication.getName());
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Error: User is not found."));
        return ResponseEntity.ok(new UserDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserDataById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: User is not found."));
        return ResponseEntity.ok(new UserDto(user));
    }

    @GetMapping()
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}
