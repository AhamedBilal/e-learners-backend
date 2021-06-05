package com.bilal.learning_platform.controller;

import com.bilal.learning_platform.dto.UserDto;
import com.bilal.learning_platform.model.ERole;
import com.bilal.learning_platform.model.Instructor;
import com.bilal.learning_platform.model.Role;
import com.bilal.learning_platform.model.User;
import com.bilal.learning_platform.payload.request.LoginRequest;
import com.bilal.learning_platform.payload.request.SignupRequest;
import com.bilal.learning_platform.payload.response.JwtResponse;
import com.bilal.learning_platform.payload.response.MessageResponse;
import com.bilal.learning_platform.repository.InstructorRepository;
import com.bilal.learning_platform.repository.RoleRepository;
import com.bilal.learning_platform.repository.UserRepository;
import com.bilal.learning_platform.security.service.UserDetailsImpl;
import com.bilal.learning_platform.security.jwt.JwtUtils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final InstructorRepository instructorRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, InstructorRepository instructorRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.instructorRepository = instructorRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        User user = userRepository.getById(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, new UserDto(user)));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        String strRole = signUpRequest.getRole();
        Role role;

        if (strRole == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            role = userRole;
        } else {
            Role adminRole = roleRepository.findByName(ERole.valueOf(strRole))
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            role = adminRole;
        }

        user.setRole(role);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> studentSignUp(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Email is already in use!");
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                .orElseThrow(() -> new RuntimeException("Role is not found."));

        user.setRole(userRole);
        user.setFname(signUpRequest.getFname());
        user.setLname(signUpRequest.getLanme());
        userRepository.save(user);
        System.out.println(user);
        return ResponseEntity.ok(new MessageResponse("Student registered successfully!"));
    }

    @PutMapping("/become-instructor")
    @Transactional
    public ResponseEntity<?> becomeInstructor(Authentication authentication) {

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User is not loggedIn."));
        Role userRole = roleRepository.findByName(ERole.ROLE_TEACHER)
                .orElseThrow(() -> new RuntimeException("Role is not found."));

        user.setRole(userRole);
        Instructor instructor = new Instructor();
        instructor.setUser(user);
        userRepository.save(user);
        instructorRepository.save(instructor);

        return ResponseEntity.ok(new MessageResponse("Teacher registered successfully!"));
    }
}
