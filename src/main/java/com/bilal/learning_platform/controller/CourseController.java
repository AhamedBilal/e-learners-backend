package com.bilal.learning_platform.controller;

import com.bilal.learning_platform.dto.CourseDto;
import com.bilal.learning_platform.model.Category;
import com.bilal.learning_platform.model.Course;
import com.bilal.learning_platform.model.Instructor;
import com.bilal.learning_platform.model.Level;
import com.bilal.learning_platform.payload.response.MessageResponse;
import com.bilal.learning_platform.repository.CategoryRepository;
import com.bilal.learning_platform.repository.CourseRepository;
import com.bilal.learning_platform.repository.InstructorRepository;
import com.bilal.learning_platform.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/courses")
@Transactional
public class CourseController {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final CategoryRepository categoryRepository;
    private final LevelRepository levelRepository;

    @Autowired
    public CourseController(CourseRepository courseRepository, InstructorRepository instructorRepository, CategoryRepository categoryRepository, LevelRepository levelRepository) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
        this.categoryRepository = categoryRepository;
        this.levelRepository = levelRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Category> all = categoryRepository.findAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody CourseDto course, Authentication authentication) {
        Instructor instructor = instructorRepository.findInstructorByUser_Username(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Instructor Not Found!"));
        System.out.println(instructor);
        Course newCourse = new Course(course);
        newCourse.setInstructor(instructor);
        Category category = categoryRepository.findById(course.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
        Level level = levelRepository.findById(course.getLevelId()).orElseThrow(() -> new RuntimeException("Level not found"));
        newCourse.setCategory(category);
        newCourse.setLevel(level);
        Course save = courseRepository.save(newCourse);
        return ResponseEntity.ok(save.getId());
    }

}
