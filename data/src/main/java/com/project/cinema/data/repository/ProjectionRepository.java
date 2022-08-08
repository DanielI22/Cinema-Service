package com.project.cinema.data.repository;

import com.project.cinema.data.entity.ProjectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectionRepository extends JpaRepository<ProjectionEntity, Long> {
}
