package com.bilal.learning_platform.dto;

import com.bilal.learning_platform.model.SectionData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class SectionDataDto {
    private Long id;
    private String status;
    private Integer completed = 0;
    private Long sectionId;
    private Long courseDataId;
    private List<LessonDataDto> lessonDatas = new ArrayList<>();

    public SectionDataDto(SectionData data) {
        this.id = data.getId();
        this.status = data.getStatus();
        this.completed = data.getCompleted();
        this.sectionId = data.getSection().getId();
        this.courseDataId = data.getCourseData().getId();
        this.lessonDatas = data.getLessonDatas().stream().map(LessonDataDto::new).collect(Collectors.toList());
    }
}
