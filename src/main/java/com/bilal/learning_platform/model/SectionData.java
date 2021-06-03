package com.bilal.learning_platform.model;

import javax.persistence.*;

@Entity
public class SectionData {
    @Id
    @GeneratedValue
    private Long id;
    private String status;
    @OneToOne()
    private Section section;
    @ManyToOne
    private CourseData courseData;
}
