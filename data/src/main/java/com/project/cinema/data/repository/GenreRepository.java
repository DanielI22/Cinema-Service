package com.project.cinema.data.repository;

import com.project.cinema.data.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByGenreName(String name);
    boolean existsByGenreName(String name);
}
