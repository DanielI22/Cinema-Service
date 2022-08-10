package com.project.cinema.data.repository;

import com.project.cinema.data.entity.Genre;
import com.project.cinema.data.entity.ProjectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectionRepository extends JpaRepository<ProjectionEntity, Long> {
    List<ProjectionEntity> findAllByGenre(Genre genre);
}
