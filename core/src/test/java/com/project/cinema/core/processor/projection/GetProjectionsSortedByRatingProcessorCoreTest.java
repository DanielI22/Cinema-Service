package com.project.cinema.core.processor.projection;

import com.project.cinema.api.model.request.EmptyRequest;
import com.project.cinema.api.model.response.projection.ProjectionResponse;
import com.project.cinema.data.entity.projection.ProjectionEntity;
import com.project.cinema.data.repository.projection.ProjectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GetProjectionsSortedByRatingProcessorCoreTest {
    @Mock
    private ProjectionRepository projectionRepository;
    @Mock
    private ConversionService conversionService;
    @InjectMocks
    private GetProjectionsSortedByRatingProcessorCore getProjectionsSortedByRatingProcessorCore;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void SortingProjectionsByRating() {

        final ProjectionEntity projection1 =  ProjectionEntity.builder()
                .projectionId(1L)
                .title("Ariel")
                .rating(5.)
                .build();
        final ProjectionEntity projection2 =  ProjectionEntity.builder()
                .projectionId(2L)
                .title("Ben Ten")
                .rating(10.)
                .build();
        final ProjectionEntity projection3 =  ProjectionEntity.builder()
                .projectionId(3L)
                .title("Spider-man")
                .rating(7.)
                .build();

        final ProjectionResponse projectionResponse1 = ProjectionResponse.builder().title("Ariel").rating("5").build();
        final ProjectionResponse projectionResponse2 = ProjectionResponse.builder().title("Ben Ten").rating("10").build();
        final ProjectionResponse projectionResponse3 = ProjectionResponse.builder().title("Spider-Man").rating("7").build();


        when(projectionRepository.findAll()).thenReturn(List.of(projection1, projection2, projection3));

        when(conversionService.convert(projection1, ProjectionResponse.class)).thenReturn(projectionResponse1);
        when(conversionService.convert(projection2, ProjectionResponse.class)).thenReturn(projectionResponse2);
        when(conversionService.convert(projection3, ProjectionResponse.class)).thenReturn(projectionResponse3);


        assertEquals("Ben Ten",getProjectionsSortedByRatingProcessorCore
                .process(new EmptyRequest()).get().getProjectionResponses().get(0).getTitle());
    }
}