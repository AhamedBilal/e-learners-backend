package com.bilal.learning_platform.repository;

import com.bilal.learning_platform.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}
