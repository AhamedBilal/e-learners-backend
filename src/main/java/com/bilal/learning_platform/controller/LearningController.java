package com.bilal.learning_platform.controller;

import com.bilal.learning_platform.dto.CourseDataDto;
import com.bilal.learning_platform.model.*;
import com.bilal.learning_platform.payload.request.CompleteRequest;
import com.bilal.learning_platform.payload.response.MessageResponse;
import com.bilal.learning_platform.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/learning")
@Transactional
public class LearningController {

    private final CourseDataRepository courseDataRepository;
    private final LessonDataRepository lessonDataRepository;
    private final SectionDataRepository sectionDataRepository;
    private final SectionRepository sectionRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    public LearningController(CourseDataRepository courseDataRepository, LessonDataRepository lessonDataRepository, SectionDataRepository sectionDataRepository, SectionRepository sectionRepository, LessonRepository lessonRepository, UserRepository userRepository) {
        this.courseDataRepository = courseDataRepository;
        this.lessonDataRepository = lessonDataRepository;
        this.sectionDataRepository = sectionDataRepository;
        this.sectionRepository = sectionRepository;
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
    }


    @GetMapping
    public ResponseEntity<?> getAvailableCourses(@RequestParam("type") String type, Authentication authentication) {


        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        List<CourseDataDto> collect = new ArrayList<>();

        if (type.equals(EStatus.COMPLETE.name()) || type.equals(EStatus.ONGOING.name())) {
            collect = courseDataRepository.findAllByUserAndStatus(user, type).stream().map(CourseDataDto::new).collect(Collectors.toList());
        } else {
            collect = courseDataRepository.findAllByUser(user).stream().map(CourseDataDto::new).collect(Collectors.toList());
        }

        return ResponseEntity.ok(collect);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getAvailableCoursesById(@PathVariable("courseId") Long courseId, Authentication authentication) {


        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));

        CourseData courseData = courseDataRepository.findByUserAndCourse_Id(user, courseId).orElseThrow(() -> new RuntimeException("Not Found"));

        return ResponseEntity.ok(new CourseDataDto(courseData));
    }

    @PostMapping("/mark")
    public ResponseEntity<?> markAsComplete(@RequestBody CompleteRequest request, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        CourseData courseData = courseDataRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Data Not found"));

        SectionData sectionData1 = sectionDataRepository.findBySection_Id(request.getSectionId()).orElseGet(() -> {
            Section section = sectionRepository.findById(request.getSectionId()).orElseThrow(() -> new RuntimeException("Not found section"));
            SectionData sectionData = new SectionData();
            sectionData.setSection(section);
            sectionData.setCourseData(courseData);
            sectionData.setCompleted(1);
            sectionData.setStatus(EStatus.ONGOING.name());
            return sectionDataRepository.save(sectionData);
        });

        LessonData lessonData = lessonDataRepository.findByLesson_Id(request.getLessonId()).orElseGet(() -> {
            Lesson lesson = lessonRepository.findById(request.getLessonId()).orElseThrow(() -> new RuntimeException("Not found lesson"));
            LessonData lessonData1 = new LessonData();
            lessonData1.setSectionData(sectionData1);
            lessonData1.setLesson(lesson);
            lessonData1.setStatus(EStatus.COMPLETE.name());
            return lessonDataRepository.save(lessonData1);
        });

        if (!lessonData.getStatus().equals(EStatus.COMPLETE.name())) {
            lessonData.setStatus(EStatus.COMPLETE.name());
            sectionData1.setCompleted(sectionData1.getCompleted() + 1);
        } else {
            lessonData.setStatus(EStatus.ONGOING.name());
            sectionData1.setCompleted(sectionData1.getCompleted() - 1);
        }
        long l = lessonDataRepository.countAllByStatusAndSectionData_CourseData_Id(EStatus.COMPLETE.name(), request.getId());
        long l1 = lessonRepository.countAllBySection_Course_Id(request.getCourseId());

        BigDecimal percentage = BigDecimal.valueOf(l).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(l1), 2, RoundingMode.HALF_UP);
        courseData.setPercentage(percentage);
        if (percentage.compareTo(BigDecimal.valueOf(100)) == 0) {
            courseData.setStatus(EStatus.COMPLETE.name());
        } else {
            courseData.setStatus(EStatus.ONGOING.name());
        }
        lessonDataRepository.save(lessonData);
        sectionDataRepository.save(sectionData1);
        CourseData save = courseDataRepository.save(courseData);

        return ResponseEntity.ok(new CourseDataDto(save));
    }

}
