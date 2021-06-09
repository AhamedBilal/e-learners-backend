package com.bilal.learning_platform.dto;

import com.bilal.learning_platform.model.CourseData;
import com.bilal.learning_platform.payload.response.CourseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CourseDataDto {
    private Long id;
    private String status;
    private BigDecimal percentage;
    private Long userId;
    private CourseResponse course;
    private List<SectionDataDto> sectionDatas = new ArrayList<>();

    public CourseDataDto(CourseData data) {
        this.id = data.getId();
        this.status = data.getStatus();
        this.percentage = data.getPercentage();
        this.userId = data.getUser().getId();
        this.course = new CourseResponse(data.getCourse());
        this.sectionDatas = data.getSectionDatas().stream().map(SectionDataDto::new).collect(Collectors.toList());
    }
}
