package com.bilal.learning_platform.repository;

import com.bilal.learning_platform.model.Course;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findCourseByInstructor_User_Username(String username);
    List<Course> findCourseByInstructor_User_Id(Long id);

    List<Course> findAllByCategory_NameAndIsPublished(String category_name, Boolean bool);

    List<Course> findAllByIsPublishedAndTitleContainingIgnoreCase(Boolean bool,String title);

    List<Course> findAllByIsPublished(Boolean isPublished, Sort students);

    Optional<Course> findByInstructor_User_UsernameAndId(String instructor_user_username, Long id);
    Optional<Course> findByIdAndIsPublished(Long id, Boolean isPublished);

    long countByInstructor_User_Username(String instructor_user_username);
}
