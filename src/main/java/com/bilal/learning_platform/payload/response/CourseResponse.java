package com.bilal.learning_platform.payload.response;

import com.bilal.learning_platform.dto.SectionDto;
import com.bilal.learning_platform.model.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CourseResponse {
    private Long id;
    private String title;
    private String subtitle;
    private String description;
    private Integer enrolled;
    private String language;
    private String instructorName;
    private String instructorUserName;
    private String imageUrl;
    private BigDecimal price;
    private Boolean isPublished;
    private Boolean isApproved;
    private Long categoryId;
    private String categoryName;
    private Long levelId;
    private String levelName;
    private Date createdAt;
    private Date updatedAt;
    private List<SectionDto> sections = new ArrayList<>();


    public CourseResponse(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.subtitle = course.getSubtitle();
        this.description = course.getDescription();
        this.language = course.getLanguage();
        this.instructorName = course.getInstructor().getUser().getFname() + " " + (course.getInstructor().getUser().getLname() == null ? "" : course.getInstructor().getUser().getLname());
        this.instructorUserName = course.getInstructor().getUser().getUsername();
        this.imageUrl = course.getImageUrl();
        this.price = course.getPrice();
        this.isPublished = course.getIsPublished();
        this.isApproved = course.getIsApproved();
        this.categoryId = course.getCategory().getId();
        this.categoryName = course.getCategory().getName();
        this.levelId = course.getLevel().getId();
        this.levelName = course.getLevel().getName();
        this.createdAt = course.getCreatedAt();
        this.updatedAt = course.getUpdatedAt();
        this.sections = course.getSections().stream().map(SectionDto::new).collect(Collectors.toList());
    }
}
