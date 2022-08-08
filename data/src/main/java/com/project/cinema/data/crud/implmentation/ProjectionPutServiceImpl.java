package com.project.cinema.data.crud.implmentation;

import com.project.api.feign.MovieClient;
import com.project.api.model.MovieResponse;
import com.project.cinema.data.crud.interfaces.ProjectionPutService;
import com.project.cinema.data.crud.mapper.ProjectionEntityToProjection;
import com.project.cinema.data.crud.model.request.ProjectionPutRequest;
import com.project.cinema.data.crud.model.response.ProjectionResponse;
import com.project.cinema.data.entity.Genre;
import com.project.cinema.data.entity.ProjectionEntity;
import com.project.cinema.data.repository.GenreRepository;
import com.project.cinema.data.repository.ProjectionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectionPutServiceImpl implements ProjectionPutService {
    private final ProjectionRepository projectionRepository;
    private final GenreRepository genreRepository;
    private final MovieClient movieClient;
    private final ProjectionEntityToProjection projectionEntityToProjection;

    public ProjectionPutServiceImpl(ProjectionRepository projectionRepository, GenreRepository genreRepository, MovieClient movieClient, ProjectionEntityToProjection projectionEntityToProjection) {
        this.projectionRepository = projectionRepository;
        this.genreRepository = genreRepository;
        this.movieClient = movieClient;
        this.projectionEntityToProjection = projectionEntityToProjection;
    }

    @Override
    public ProjectionResponse updateProjection(Long id, ProjectionPutRequest projectionPutRequest) {
        Optional<ProjectionEntity> projection = projectionRepository.findById(id);

        MovieResponse movieResponse = movieClient.getMovie(projectionPutRequest.getMovieId());

        if(!genreRepository.existsByGenreName(movieResponse.getGenre())) {
            Genre genre = new Genre();
            genre.setGenreName(movieResponse.getGenre());
            genreRepository.save(genre);
        }

        projection.get().setTitle(movieResponse.getTitle());
        projection.get().setDescription(movieResponse.getDescription());
        projection.get().setGenreId(genreRepository.findByGenreName(movieResponse.getGenre()).getGenreId());
        projection.get().setReleaseDate(movieResponse.getReleaseDate());
        projection.get().setRating(movieResponse.getRating());
        projection.get().setProjectionDate(projectionPutRequest.getDate());
        projection.get().setProjectionTime(projectionPutRequest.getTime());
        projection.get().setTicketPrice(projectionPutRequest.getTicketPrice());
        projection.get().setCapacity(projectionPutRequest.getCapacity());
        projectionRepository.save(projection.get());

        return projectionEntityToProjection.map(projection.get());
    }
}
