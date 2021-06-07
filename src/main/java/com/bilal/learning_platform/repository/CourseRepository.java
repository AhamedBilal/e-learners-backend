package com.bilal.learning_platform.repository;

import com.bilal.learning_platform.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findCourseByInstructor_User_Username(String username);
    List<Course> findCourseByInstructor_User_Id(Long id);
}
