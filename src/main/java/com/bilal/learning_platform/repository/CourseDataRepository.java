package com.bilal.learning_platform.repository;

import com.bilal.learning_platform.model.CourseData;
import com.bilal.learning_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseDataRepository extends JpaRepository<CourseData, Long> {
    List<CourseData> findAllByUserAndStatus(User user, String s);
    List<CourseData> findAllByUser(User user);

    Optional<CourseData> findByUserAndCourse_Id(User user, Long id);
}
