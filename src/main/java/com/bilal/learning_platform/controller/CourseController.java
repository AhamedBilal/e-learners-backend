package com.bilal.learning_platform.controller;

import com.bilal.learning_platform.dto.CourseDto;
import com.bilal.learning_platform.model.*;
import com.bilal.learning_platform.payload.request.PublishRequest;
import com.bilal.learning_platform.payload.response.CheckValidityResponse;
import com.bilal.learning_platform.payload.response.CourseResponse;
import com.bilal.learning_platform.payload.response.MessageResponse;
import com.bilal.learning_platform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final InstructorRepository instructorRepository;
    private final CategoryRepository categoryRepository;
    private final LevelRepository levelRepository;
    private final CourseDataRepository courseDataRepository;
    private final WishlistRepository wishlistRepository;

    @Autowired
    public CourseController(CourseRepository courseRepository, InstructorRepository instructorRepository, CategoryRepository categoryRepository, LevelRepository levelRepository, CourseDataRepository courseDataRepository, WishlistRepository wishlistRepository) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
        this.categoryRepository = categoryRepository;
        this.levelRepository = levelRepository;
        this.courseDataRepository = courseDataRepository;
        this.wishlistRepository = wishlistRepository;
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getAll() {
        List<CourseResponse> all = courseRepository.findAll().stream().map(CourseResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(all);
    }

    @GetMapping("/general/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getByIdPublished(@PathVariable Long id) {
        Course course = courseRepository.findByIdAndIsPublished(id, true).orElseThrow(() -> new RuntimeException("Course not Found!"));
        CourseResponse courseResponse = new CourseResponse(course);
        return ResponseEntity.ok(courseResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not Found!"));
        CourseResponse courseResponse = new CourseResponse(course);
        return ResponseEntity.ok(courseResponse);
    }


    @GetMapping("/search")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> searchByTitle(@RequestParam("k") String keyword) {
        List<CourseResponse> collect = courseRepository.findAllByIsPublishedAndTitleContainingIgnoreCase(true, keyword).stream().map(CourseResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }

    @GetMapping("/category/{name}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> searchByCategoryName(@PathVariable String name) {
        List<CourseResponse> collect = courseRepository.findAllByCategory_NameAndIsPublished(name, true).stream().map(CourseResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
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
        newCourse.setPrice(course.getPrice());
        Course save = courseRepository.save(newCourse);
        return ResponseEntity.ok(save.getId());
    }

    @PutMapping
    public ResponseEntity<?> updateCourse(@RequestBody CourseDto course, Authentication authentication) {
        Course newCourse = courseRepository.findByInstructor_User_UsernameAndId(authentication.getName(), course.getId())
                .orElseThrow(() -> new RuntimeException("Instructor Not Found!"));
        Category category = categoryRepository.findById(course.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
        Level level = levelRepository.findById(course.getLevelId()).orElseThrow(() -> new RuntimeException("Level not found"));

        newCourse.setTitle(course.getTitle());
        newCourse.setSubtitle(course.getSubtitle());
        newCourse.setDescription(course.getDescription());
        newCourse.setDescription(course.getDescription());
        newCourse.setImageUrl(course.getImageUrl());
        newCourse.setCategory(category);
        newCourse.setLevel(level);
        newCourse.setPrice(course.getPrice());
        Course save = courseRepository.save(newCourse);
        return ResponseEntity.ok(save.getId());
    }

    @PutMapping("/publish")
    public ResponseEntity<?> publishCourse(@RequestBody PublishRequest course, Authentication authentication) {
        Course newCourse = courseRepository.findByInstructor_User_UsernameAndId(authentication.getName(), course.getId())
                .orElseThrow(() -> new RuntimeException("Instructor Not Found!"));
        newCourse.setIsPublished(true);
        courseRepository.save(newCourse);
        return ResponseEntity.ok(new MessageResponse("Published"));
    }

    @PutMapping("/unpublish")
    public ResponseEntity<?> unPublishCourse(@RequestBody PublishRequest course, Authentication authentication) {
        Course newCourse = courseRepository.findByInstructor_User_UsernameAndId(authentication.getName(), course.getId())
                .orElseThrow(() -> new RuntimeException("Instructor Not Found!"));
        newCourse.setIsPublished(false);
        courseRepository.save(newCourse);
        return ResponseEntity.ok(new MessageResponse("Unpublished"));
    }

    @GetMapping("/detail/{courseId}")
    public ResponseEntity<?> getCourseUserDetail(@PathVariable Long courseId, Authentication authentication) {
        boolean isBought = courseDataRepository.existsByCourse_IdAndUser_Username(courseId, authentication.getName());
        boolean isWished = wishlistRepository.existsByCourse_IdAndUser_Username(courseId, authentication.getName());
        return ResponseEntity.ok(new CheckValidityResponse(isBought, isWished));
    }


    @GetMapping("/top")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getTopCourses() {
        List<CourseResponse> courseResponses = courseRepository.findAllByIsPublished(true, Sort.by(Sort.Direction.DESC, "students")).stream().map(CourseResponse::new).collect(Collectors.toList());
        ;
        return ResponseEntity.ok(courseResponses);
    }

}
