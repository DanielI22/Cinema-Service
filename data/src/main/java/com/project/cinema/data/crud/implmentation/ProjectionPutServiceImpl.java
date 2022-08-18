package com.project.cinema.data.crud.implmentation;

import com.project.api.feign.MovieClient;
import com.project.api.model.MovieResponse;
import com.project.cinema.data.crud.exception.MovieNotFoundException;
import com.project.cinema.data.crud.exception.ServerUnavailableException;
import com.project.cinema.data.crud.interfaces.ProjectionPutService;
import com.project.cinema.data.crud.mapper.ProjectionEntityToProjectionResponse;
import com.project.cinema.data.crud.model.request.ProjectionPutRequest;
import com.project.cinema.data.crud.model.response.ProjectionResponse;
import com.project.cinema.data.entity.projection.Genre;
import com.project.cinema.data.entity.projection.ProjectionEntity;
import com.project.cinema.data.repository.projection.GenreRepository;
import com.project.cinema.data.repository.projection.ProjectionRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;


@Service
public class ProjectionPutServiceImpl implements ProjectionPutService {
    private final ProjectionRepository projectionRepository;
    private final GenreRepository genreRepository;
    private final MovieClient movieClient;
    private final ProjectionEntityToProjectionResponse projectionEntityToProjection;

    public ProjectionPutServiceImpl(ProjectionRepository projectionRepository, GenreRepository genreRepository, MovieClient movieClient, ProjectionEntityToProjectionResponse projectionEntityToProjection) {
        this.projectionRepository = projectionRepository;
        this.genreRepository = genreRepository;
        this.movieClient = movieClient;
        this.projectionEntityToProjection = projectionEntityToProjection;
    }

    @Override
    public ProjectionResponse updateProjection(Long id, ProjectionPutRequest projectionPutRequest) {
        ProjectionEntity projection = projectionRepository.findById(id).orElseThrow(MovieNotFoundException::new);

        MovieResponse movieResponse;
        try {
            movieResponse = movieClient.getMovie(projectionPutRequest.getMovieId());
        }
        catch (FeignException.FeignClientException e) {
            throw new MovieNotFoundException();
        }
        catch (FeignException e) {
            throw new ServerUnavailableException();
        }

        if(!genreRepository.existsByGenreName(movieResponse.getGenre())) {
            Genre genre = new Genre();
            genre.setGenreName(movieResponse.getGenre());
            genreRepository.save(genre);
        }

        projection.setTitle(movieResponse.getTitle());
        projection.setDescription(movieResponse.getDescription());
        projection.setGenreId(genreRepository.findByGenreName(movieResponse.getGenre()).orElseThrow().getGenreId());
        projection.setReleaseDate(movieResponse.getReleaseDate());
        projection.setRating(movieResponse.getRating());
        projection.setProjectionDate(projectionPutRequest.getDate());
        projection.setProjectionTime(projectionPutRequest.getTime());
        projection.setTicketPrice(projectionPutRequest.getTicketPrice());
        projection.setCapacity(projectionPutRequest.getCapacity());
        projectionRepository.save(projection);

        return projectionEntityToProjection.map(projection);
    }
}
