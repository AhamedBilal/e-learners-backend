package com.bilal.learning_platform.model;

import javax.persistence.*;

@Entity
public class CourseData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    @OneToOne()
    private User user;
    @OneToOne()
    private Course course;
}
