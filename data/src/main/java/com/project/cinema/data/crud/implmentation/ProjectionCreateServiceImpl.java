package com.project.cinema.data.crud.implmentation;

import com.project.api.feign.MovieClient;
import com.project.api.model.MovieResponse;
import com.project.cinema.data.crud.exception.MovieNotFoundException;
import com.project.cinema.data.crud.exception.ServerUnavailableException;
import com.project.cinema.data.crud.interfaces.ProjectionCreateService;
import com.project.cinema.data.crud.model.request.ProjectionCreateRequest;
import com.project.cinema.data.entity.projection.Genre;
import com.project.cinema.data.entity.projection.ProjectionEntity;
import com.project.cinema.data.repository.projection.GenreRepository;
import com.project.cinema.data.repository.projection.ProjectionRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;

@Service
public class ProjectionCreateServiceImpl implements ProjectionCreateService {
    private final ProjectionRepository projectionRepository;
    private final GenreRepository genreRepository;
    private final MovieClient movieClient;

    public ProjectionCreateServiceImpl(ProjectionRepository projectionRepository, GenreRepository genreRepository, MovieClient movieClient) {
        this.projectionRepository = projectionRepository;
        this.genreRepository = genreRepository;
        this.movieClient = movieClient;
    }

    public Long createProjection(ProjectionCreateRequest projectionCreateRequest) {

        MovieResponse movieResponse;
        try {
            movieResponse = movieClient.getMovie(projectionCreateRequest.getMovieId());
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

        ProjectionEntity projection =new ProjectionEntity();
        projection.setTitle(movieResponse.getTitle());
        projection.setDescription(movieResponse.getDescription());
        projection.setGenreId(genreRepository.findByGenreName(movieResponse.getGenre()).orElseThrow().getGenreId());
        projection.setReleaseDate(movieResponse.getReleaseDate());
        projection.setRating(movieResponse.getRating());
        projection.setProjectionDate(projectionCreateRequest.getDate());
        projection.setProjectionTime(projectionCreateRequest.getTime());
        projection.setTicketPrice(projectionCreateRequest.getTicketPrice());
        projection.setCapacity(projectionCreateRequest.getCapacity());
        projectionRepository.save(projection);

        return projection.getProjectionId();
    }
}
