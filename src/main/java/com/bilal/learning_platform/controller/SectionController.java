package com.bilal.learning_platform.controller;

import com.bilal.learning_platform.dto.SectionDto;
import com.bilal.learning_platform.model.Course;
import com.bilal.learning_platform.model.Section;
import com.bilal.learning_platform.payload.response.MessageResponse;
import com.bilal.learning_platform.repository.CourseRepository;
import com.bilal.learning_platform.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/section")
@Transactional
public class SectionController {

    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;

    @Autowired
    public SectionController(CourseRepository courseRepository, SectionRepository sectionRepository) {
        this.courseRepository = courseRepository;
        this.sectionRepository = sectionRepository;
    }

    @PostMapping
    public ResponseEntity<?> addSection(@RequestBody SectionDto sectionDto) {
        Course course = courseRepository.findById(sectionDto.getCourseId()).orElseThrow(() -> new RuntimeException("Course not found"));
        Section section = new Section();
        section.setCourse(course);
        section.setDescription(sectionDto.getDescription());
        section.setTitle(sectionDto.getTitle());
        section.setIndexNumber(sectionDto.getIndexNumber());
        Section save = sectionRepository.save(section);
        return ResponseEntity.ok(new SectionDto(save));
    }

    @PutMapping
    public ResponseEntity<?> updateSection(@RequestBody SectionDto sectionDto) {
        Section section = sectionRepository.findById(sectionDto.getId()).orElseThrow(() -> new RuntimeException("Course not found"));
        section.setDescription(sectionDto.getDescription());
        section.setTitle(sectionDto.getTitle());
        section.setIndexNumber(sectionDto.getIndexNumber());
        Section save = sectionRepository.save(section);
        return ResponseEntity.ok(new SectionDto(save));
    }

    @DeleteMapping("/{sectionId}")
    public ResponseEntity<?> deleteSectionById(@PathVariable Long sectionId) {
        sectionRepository.deleteById(sectionId);
        return ResponseEntity.ok(new MessageResponse("Deleted Successfully!"));
    }

    @GetMapping("courseId/{courseId}/")
    public ResponseEntity<?> getSectionByCourseId(@PathVariable Long courseId) {
        List<SectionDto> all = sectionRepository.findAllByCourse_Id(courseId).stream().map(SectionDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{sectionId}")
    public ResponseEntity<?> getSectionById(@PathVariable Long sectionId) {
        Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new RuntimeException("section not found"));
        return ResponseEntity.ok(new SectionDto(section));
    }

}
