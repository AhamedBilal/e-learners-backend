package com.bilal.learning_platform.dto;

import com.bilal.learning_platform.model.Lesson;
import com.bilal.learning_platform.model.LessonData;
import com.bilal.learning_platform.model.SectionData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
public class LessonDataDto {
    private Long id;
    private String status;
    private Long lessonId;
    private Long sectionDataId;

    public LessonDataDto(LessonData data) {
        this.id = data.getId();
        this.status = data.getStatus();
        this.lessonId = data.getLesson().getId();
        this.sectionDataId = data.getSectionData().getId();
    }
}
