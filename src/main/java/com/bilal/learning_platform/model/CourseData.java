package com.bilal.learning_platform.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CourseData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private BigDecimal percentage = BigDecimal.valueOf(0);
    @ManyToOne()
    private User user;
    @OneToOne()
    private Course course;
    @OneToMany(mappedBy = "courseData")
    private List<SectionData> sectionDatas = new ArrayList<>();
}
