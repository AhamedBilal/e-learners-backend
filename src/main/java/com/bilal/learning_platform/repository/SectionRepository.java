package com.bilal.learning_platform.repository;

import com.bilal.learning_platform.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findAllByCourse_Id(Long id);
}
