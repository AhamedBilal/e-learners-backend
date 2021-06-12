package com.bilal.learning_platform.repository;

import com.bilal.learning_platform.model.CourseData;
import com.bilal.learning_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

public interface CourseDataRepository extends JpaRepository<CourseData, Long> {
    List<CourseData> findAllByUserAndStatus(User user, String s);
    List<CourseData> findAllByUser(User user);

    boolean existsByCourse_IdAndUser_Username(Long course_id, String user_username);

    Optional<CourseData> findByUserAndCourse_Id(User user, Long id);
}
