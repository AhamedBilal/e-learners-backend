package com.bilal.learning_platform.model;

import com.bilal.learning_platform.dto.LessonDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String type;
    private String content;
    private int indexNumber;
    @ManyToOne()
    private Section section;

    public Lesson(LessonDto lesson) {
        this.id = lesson.getId();
        this.title = lesson.getTitle();
        this.type = lesson.getType();
        this.content = lesson.getContent();
        this.indexNumber = lesson.getIndexNumber();
    }
}
