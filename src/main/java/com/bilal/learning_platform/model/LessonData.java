package com.bilal.learning_platform.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class LessonData {
    @Id
    @GeneratedValue
    private Long id;
    private String status;
    @OneToOne()
    private Lesson lesson;
    @ManyToOne()
    private SectionData sectionData;
}
