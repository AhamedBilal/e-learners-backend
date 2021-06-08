package com.bilal.learning_platform.repository;

import com.bilal.learning_platform.model.CourseData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseDataRepository extends JpaRepository<CourseData, Long> {
}
