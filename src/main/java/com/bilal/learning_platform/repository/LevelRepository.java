package com.bilal.learning_platform.repository;

import com.bilal.learning_platform.model.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<Level, Long> {
}
