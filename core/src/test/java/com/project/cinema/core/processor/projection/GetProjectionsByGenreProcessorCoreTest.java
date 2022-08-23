package com.project.cinema.core.processor.projection;

import com.project.cinema.api.model.request.projection.GetProjectionsByGenreRequest;
import com.project.cinema.api.model.response.projection.GetProjectionsByGenreResponse;
import com.project.cinema.api.model.response.projection.ProjectionResponse;
import com.project.cinema.data.entity.projection.Genre;
import com.project.cinema.data.entity.projection.ProjectionEntity;
import com.project.cinema.data.repository.projection.GenreRepository;
import com.project.cinema.data.repository.projection.ProjectionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GetProjectionsByGenreProcessorCoreTest {

    @Mock
    private ProjectionRepository projectionRepository;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private ConversionService conversionService;
    @InjectMocks
    private GetProjectionsByGenreProcessorCore getProjectionsByGenreProcessorCore;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIfEveryProjectionIsWithTheRequestedGenre() {
        final Genre genre = Genre.builder().genreId(1L).genreName("Crime").build();
        final ProjectionEntity projection2 =  ProjectionEntity.builder()
                .projectionId(2L)
                .title("Orient Express")
                .rating(10.)
                .genre(genre)
                .build();
        final ProjectionEntity projection3 =  ProjectionEntity.builder()
                .projectionId(3L)
                .title("Sherlock Holmes")
                .rating(7.)
                .genre(genre)
                .build();

        GetProjectionsByGenreRequest getProjectionsByGenreRequest =
                GetProjectionsByGenreRequest.builder().genreName("Crime").build();

        when(genreRepository.findByGenreName(getProjectionsByGenreRequest.getGenreName())).thenReturn(Optional.of(genre));
        when(projectionRepository.findAllByGenre(genre)).thenReturn(List.of(projection2, projection3));
        when(conversionService.convert(projection2, ProjectionResponse.class))
                .thenReturn(ProjectionResponse.builder().title("Spider-man").rating("10").genre("Crime").build());

        when(conversionService.convert(projection3, ProjectionResponse.class))
                .thenReturn(ProjectionResponse.builder().title("Spider-man").rating("7").genre("Crime").build());

        GetProjectionsByGenreResponse getProjectionsByGenreResponse =
                getProjectionsByGenreProcessorCore.process(getProjectionsByGenreRequest).get();

        assertTrue(getProjectionsByGenreResponse
                .getProjectionResponses().stream().allMatch(pr -> pr.getGenre().equals("Crime")));
    }
}