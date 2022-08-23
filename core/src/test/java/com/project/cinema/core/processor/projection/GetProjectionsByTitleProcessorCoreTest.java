package com.project.cinema.core.processor.projection;

import com.project.cinema.api.model.request.projection.GetProjectionsByTitleRequest;
import com.project.cinema.api.model.response.projection.GetProjectionsByTitleResponse;
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

class GetProjectionsByTitleProcessorCoreTest {
    @Mock
    private ProjectionRepository projectionRepository;
    @Mock
    private ConversionService conversionService;
    @InjectMocks
    private GetProjectionsByTitleProcessorCore getProjectionsByTitleProcessorCore;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTitleListCount() {
        final ProjectionEntity projection2 =  ProjectionEntity.builder()
                .projectionId(2L)
                .title("Spider-man")
                .rating(10.)
                .build();
        final ProjectionEntity projection3 =  ProjectionEntity.builder()
                .projectionId(3L)
                .title("Spider-man")
                .rating(7.)
                .build();

        GetProjectionsByTitleRequest getProjectionsByTitleRequest = GetProjectionsByTitleRequest.builder().title("Spider-man").build();

        when(projectionRepository.findAllByTitle(getProjectionsByTitleRequest.getTitle())).thenReturn(List.of(projection2, projection3));
        when(conversionService.convert(projection2, ProjectionResponse.class))
                .thenReturn(ProjectionResponse.builder().title("Spider-man").rating("10").build());

        when(conversionService.convert(projection3, ProjectionResponse.class))
                .thenReturn(ProjectionResponse.builder().title("Spider-man").rating("7").build());

        GetProjectionsByTitleResponse getProjectionsByTitleResponse =
                getProjectionsByTitleProcessorCore.process(getProjectionsByTitleRequest).get();

        assertEquals("Spider-man", getProjectionsByTitleResponse.getTitle());
        assertEquals(2, getProjectionsByTitleResponse.getProjectionResponses().size());
    }
}