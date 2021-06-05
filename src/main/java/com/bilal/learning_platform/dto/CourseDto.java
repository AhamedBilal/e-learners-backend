package com.bilal.learning_platform.dto;

import com.bilal.learning_platform.model.Category;
import com.bilal.learning_platform.model.Instructor;
import com.bilal.learning_platform.model.Level;
import com.bilal.learning_platform.model.Section;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class CourseDto {
    private String title;
    private String subtitle;
    private String description;
    private String language;
    private String imageUrl;
    private Integer price;
    private final Boolean isPublished = false;
    private final Boolean isApproved = false;
    private Long categoryId;
    private Long levelId;

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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }
}
