package com.bilal.learning_platform.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class SectionData {
    @Id
    @GeneratedValue
    private Long id;
    private String status;
    private Integer completed = 0;
    @OneToOne()
    private Section section;
    @ManyToOne
    private CourseData courseData;
    @OneToMany(mappedBy = "sectionData")
    private List<LessonData> lessonDatas = new ArrayList<>();
}
