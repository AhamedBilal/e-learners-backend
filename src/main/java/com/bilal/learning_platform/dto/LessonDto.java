package com.bilal.learning_platform.dto;

import com.bilal.learning_platform.model.Lesson;
import com.bilal.learning_platform.model.Section;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
public class LessonDto {
    private Long id;
    private String title;
    private String type;
    private String content;
    private int indexNumber;
    private Long sectionId;

    public LessonDto(Lesson l) {
        this.id = l.getId();
        this.type = l.getType();
        this.title = l.getTitle();
        this.content = l.getContent();
        this.indexNumber = l.getIndexNumber();
        this.sectionId = l.getSection().getId();
    }
}
