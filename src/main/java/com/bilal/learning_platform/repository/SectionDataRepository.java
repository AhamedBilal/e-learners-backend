package com.bilal.learning_platform.repository;

import com.bilal.learning_platform.model.SectionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectionDataRepository extends JpaRepository<SectionData, Long> {
    Optional<SectionData> findBySection_Id(Long aLong);
}
