package com.bilal.learning_platform.controller;

import com.bilal.learning_platform.dto.LessonDto;
import com.bilal.learning_platform.model.Lesson;
import com.bilal.learning_platform.model.Section;
import com.bilal.learning_platform.repository.LessonRepository;
import com.bilal.learning_platform.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/lesson")
@Transactional
public class LessonController {

    private final SectionRepository sectionRepository;
    private final LessonRepository lessonRepository;

    @Autowired
    public LessonController(SectionRepository sectionRepository, LessonRepository lessonRepository) {
        this.sectionRepository = sectionRepository;
        this.lessonRepository = lessonRepository;
    }

    @PostMapping
    public ResponseEntity<?> addLessons(@RequestBody LessonDto lessonDto) {
        Section section = sectionRepository.findById(lessonDto.getSectionId()).orElseThrow(() -> new RuntimeException("Lesson not found!"));
        Lesson lesson = new Lesson(lessonDto);
        lesson.setSection(section);
        Lesson save = lessonRepository.save(lesson);
        return ResponseEntity.ok(new LessonDto(save));
    }

    @PutMapping
    public ResponseEntity<?> updateLessons(@RequestBody LessonDto lessonDto) {
        Lesson lesson = lessonRepository.findById(lessonDto.getId()).orElseThrow(() -> new RuntimeException("Lesson not found!"));
        lesson.setContent(lessonDto.getContent());
        lesson.setTitle(lessonDto.getTitle());
        lesson.setType(lessonDto.getType());
        Lesson save = lessonRepository.save(lesson);
        return ResponseEntity.ok(new LessonDto(save));
    }

}
