package com.bilal.learning_platform.model;

import javax.persistence.*;

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
