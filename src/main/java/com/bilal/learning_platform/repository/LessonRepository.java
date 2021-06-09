package com.bilal.learning_platform.repository;

import com.bilal.learning_platform.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    long countAllBySection_Course_Id(Long section_course_id);
}
