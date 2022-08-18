package com.project.cinema.data.repository.projection;

import com.project.cinema.data.entity.projection.Genre;
import com.project.cinema.data.entity.projection.ProjectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectionRepository extends JpaRepository<ProjectionEntity, Long> {
    List<ProjectionEntity> findAllByGenre(Genre genre);

    @Query("FROM ProjectionEntity p WHERE p.title LIKE :title%")
    List<ProjectionEntity> findAllByTitle(String title);

    List<ProjectionEntity> findAllByProjectionDateBetween(LocalDate startDate, LocalDate endDate);
}
