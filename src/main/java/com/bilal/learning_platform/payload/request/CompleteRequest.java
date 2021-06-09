package com.bilal.learning_platform.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompleteRequest {
    private Long id;
    private Long courseId;
    private Long sectionId;
    private Long lessonId;
    private Boolean isCompleted;
}
