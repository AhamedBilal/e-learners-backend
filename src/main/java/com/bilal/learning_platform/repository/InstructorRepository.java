package com.bilal.learning_platform.repository;

import com.bilal.learning_platform.model.Instructor;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    Optional<Instructor> findInstructorByUser_Username(String username);
}
