package com.bilal.learning_platform.dto;

import com.bilal.learning_platform.model.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CourseDto {
    private Long id;
    private String title;
    private String subtitle;
    private String description;
    private String language;
    private String imageUrl;
    private Integer price;
    private Boolean isPublished;
    private Boolean isApproved;
    private Long categoryId;
    private Long levelId;
    private Date createdAt;
    private Date updatedAt;
    private List<SectionDto> sections = new ArrayList<>();

    public CourseDto(Course c) {
        this.id = c.getId();
        this.title = c.getTitle();
        this.subtitle = c.getSubtitle();
        this.description = c.getDescription();
        this.language = c.getLanguage();
        this.imageUrl = c.getImageUrl();
        this.price = c.getPrice();
        this.isPublished = c.getIsPublished();
        this.isApproved = c.getIsApproved();
        this.categoryId = c.getCategory().getId();
        this.levelId = c.getLevel().getId();
        this.createdAt = c.getCreatedAt();
        this.updatedAt = c.getUpdatedAt();
        this.sections = c.getSections().stream().map(SectionDto::new).collect(Collectors.toList());
    }
}
