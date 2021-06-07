package com.bilal.learning_platform.model;

import com.bilal.learning_platform.dto.CourseDto;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private Integer price;
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

    public Course() {
    }

    public Course(CourseDto dto) {
        this.title = dto.getTitle();
        this.subtitle = dto.getSubtitle();
        this.description = dto.getDescription();
        this.imageUrl = dto.getImageUrl();
    }

    public Course(Long id, String title, String subtitle, String description, String language, String imageUrl, Integer price, Category category, Level level, Instructor instructor, List<Section> sections) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}
