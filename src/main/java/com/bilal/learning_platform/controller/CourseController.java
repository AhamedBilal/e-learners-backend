package com.bilal.learning_platform.controller;

import com.bilal.learning_platform.dto.CourseDto;
import com.bilal.learning_platform.dto.SectionDto;
import com.bilal.learning_platform.model.*;
import com.bilal.learning_platform.payload.response.MessageResponse;
import com.bilal.learning_platform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/courses")
@Transactional
public class CourseController {

    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;
    private final LessonRepository lessonRepository;
    private final InstructorRepository instructorRepository;
    private final CategoryRepository categoryRepository;
    private final LevelRepository levelRepository;

    @Autowired
    public CourseController(CourseRepository courseRepository, SectionRepository sectionRepository, LessonRepository lessonRepository, InstructorRepository instructorRepository, CategoryRepository categoryRepository, LevelRepository levelRepository) {
        this.courseRepository = courseRepository;
        this.sectionRepository = sectionRepository;
        this.lessonRepository = lessonRepository;
        this.instructorRepository = instructorRepository;
        this.categoryRepository = categoryRepository;
        this.levelRepository = levelRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Course> all = courseRepository.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not Found!"));
        CourseDto courseDto = new CourseDto(course);
        return ResponseEntity.ok(courseDto);
    }

    @GetMapping("/instructor")
    public ResponseEntity<?> getAllCoursesForInstructor(Authentication authentication) {
        List<CourseDto> all = courseRepository.findCourseByInstructor_User_Username(authentication.getName()).stream().map(CourseDto::new).collect(Collectors.toList());
        System.out.println("Courses : " + all);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/instructor/{id}")
    public ResponseEntity<?> getAllCoursesByUserId(@PathVariable Long id) {
        List<CourseDto> all = courseRepository.findCourseByInstructor_User_Id(id).stream().map(CourseDto::new).collect(Collectors.toList());
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
