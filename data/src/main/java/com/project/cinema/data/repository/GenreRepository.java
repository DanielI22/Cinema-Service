package com.project.cinema.data.repository;

import com.project.cinema.data.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByGenreName(String name);
    boolean existsByGenreName(String name);
}
