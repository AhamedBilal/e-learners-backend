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
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int indexNumber;
    @OneToMany(mappedBy = "section", cascade = {CascadeType.REMOVE})
    private List<Lesson> lessons = new ArrayList<>();
    @ManyToOne()
    private Course course;
}
