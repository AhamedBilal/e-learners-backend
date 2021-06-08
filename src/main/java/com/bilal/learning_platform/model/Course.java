package com.bilal.learning_platform.model;

import com.bilal.learning_platform.dto.CourseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String subtitle;
    @Column(length = 10000)
    private String description;
    private String language;
    private String imageUrl;
    private BigDecimal price = BigDecimal.valueOf(0);
    private Integer students = 0;
    private final Boolean isPublished = false;
    private final Boolean isApproved = false;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @ManyToOne()
    private Category category;
    @ManyToOne()
    private Level level;
    @ManyToOne()
    private Instructor instructor;
    @OneToMany(mappedBy = "course", cascade = {CascadeType.REMOVE})
    private List<Section> sections = new ArrayList<>();

    public Course(CourseDto dto) {
        this.title = dto.getTitle();
        this.subtitle = dto.getSubtitle();
        this.description = dto.getDescription();
        this.imageUrl = dto.getImageUrl();
    }

    public Course(Long id, String title, String subtitle, String description, String language, String imageUrl, BigDecimal price, Category category, Level level, Instructor instructor, List<Section> sections) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.language = language;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
        this.level = level;
        this.instructor = instructor;
        this.sections = sections;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Course)) {
            return false;
        }
        Course course = (Course) obj;
        return id.equals(course.id);
    }
}
