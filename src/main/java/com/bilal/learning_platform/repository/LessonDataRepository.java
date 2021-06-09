package com.bilal.learning_platform.repository;

import com.bilal.learning_platform.model.LessonData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonDataRepository extends JpaRepository<LessonData, Long> {

    Optional<LessonData> findByLesson_Id(Long aLong);

    long countAllByStatusAndSectionData_CourseData_Id(String status, Long courseDataId);
}
