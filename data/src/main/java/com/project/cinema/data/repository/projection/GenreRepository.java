package com.project.cinema.data.repository.projection;

import com.project.cinema.data.entity.projection.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByGenreName(String name);
    boolean existsByGenreName(String name);
}
