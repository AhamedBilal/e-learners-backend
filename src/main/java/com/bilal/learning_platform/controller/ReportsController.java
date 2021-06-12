package com.bilal.learning_platform.controller;

import com.bilal.learning_platform.model.Instructor;
import com.bilal.learning_platform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReportsController {

    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    @Autowired
    public ReportsController(UserRepository userRepository, InstructorRepository instructorRepository, CourseRepository courseRepository, OrderRepository orderRepository, OrderDetailsRepository orderDetailsRepository) {
        this.userRepository = userRepository;
        this.instructorRepository = instructorRepository;
        this.courseRepository = courseRepository;
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }


    @GetMapping("/instructor")
    public ResponseEntity<?> getInstructorReports(Authentication authentication) {
        long courseCount = courseRepository.countByInstructor_User_Username(authentication.getName());
        Instructor instructor = instructorRepository.findInstructorByUser_Username(authentication.getName()).orElseThrow(() -> new RuntimeException("user not found"));

        return ResponseEntity.ok("");
    }

}
