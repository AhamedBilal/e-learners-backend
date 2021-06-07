package com.bilal.learning_platform.dto;

import com.bilal.learning_platform.model.Course;
import com.bilal.learning_platform.model.Lesson;
import com.bilal.learning_platform.model.Section;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class SectionDto {
    private Long id;
    private String title;
    private String description;
    private int indexNumber;
    private Long courseId;
    private List<LessonDto> lessons = new ArrayList<>();

    public SectionDto(Section s) {
        this.id = s.getId();
        this.title = s.getTitle();
        this.description = s.getDescription();
        this.indexNumber = s.getIndexNumber();
        this.courseId = s.getCourse().getId();
        this.lessons = s.getLessons().stream().map(LessonDto::new).collect(Collectors.toList());
    }
}
