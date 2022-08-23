package com.project.cinema.core.processor.projection;

import com.project.cinema.api.error.projection.ProjectionsNotFoundError;
import com.project.cinema.api.model.request.projection.GetProjectionsByDateRequest;
import com.project.cinema.api.model.response.projection.GetProjectionsByDateResponse;
import com.project.cinema.api.model.response.projection.ProjectionResponse;
import com.project.cinema.data.entity.projection.ProjectionEntity;
import com.project.cinema.data.repository.projection.ProjectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GetProjectionsByDateProcessorCoreTest {
    @Mock
    private ProjectionRepository projectionRepository;
    @Mock
    private ConversionService conversionService;
    @InjectMocks
    private GetProjectionsByDateProcessorCore getProjectionsByDateProcessorCore;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testForSuccessfulResponse(){
        final ProjectionEntity projection1 =  ProjectionEntity.builder()
                .projectionId(2L)
                .title("Spider-man")
                .projectionDate(LocalDate.of(2022,11,22))
                .rating(10.)
                .build();
        final ProjectionEntity projection2 =  ProjectionEntity.builder()
                .projectionId(3L)
                .title("Ariel")
                .projectionDate(LocalDate.of(2022,12,22))
                .rating(7.)
                .build();

        GetProjectionsByDateRequest getProjectionsByDateRequest =
                GetProjectionsByDateRequest.builder()
                        .startProjectionDate(LocalDate.of(2022,10,22))
                        .endProjectionDate(LocalDate.of(2022,12,22))
                        .build();

        when(projectionRepository.findAllByProjectionDateBetween
                (getProjectionsByDateRequest.getStartProjectionDate(), getProjectionsByDateRequest.getEndProjectionDate()))
                .thenReturn(List.of(projection1, projection2));

        when(conversionService.convert(projection1, ProjectionResponse.class))
                .thenReturn(ProjectionResponse.builder()
                        .title("Spider-man")
                        .rating("10")
                        .projectionDate("2022/11/22")
                        .build());

        when(conversionService.convert(projection2, ProjectionResponse.class))
                .thenReturn(ProjectionResponse.builder()
                        .title("Spider-man")
                        .rating("7")
                        .projectionDate("2022/12/22")
                        .build());

        GetProjectionsByDateResponse getProjectionsByTitleResponse =
                getProjectionsByDateProcessorCore.process(getProjectionsByDateRequest).get();

        assertEquals(2, getProjectionsByTitleResponse.getProjectionResponses().size());
    }

    @Test
    public void testForNotFoundProjectionsInThisPeriod() {
        GetProjectionsByDateRequest getProjectionsByDateRequest =
                GetProjectionsByDateRequest.builder()
                        .startProjectionDate(LocalDate.of(2012,10,22))
                        .endProjectionDate(LocalDate.of(2013,12,22))
                        .build();

        when(projectionRepository.findAllByProjectionDateBetween
                (getProjectionsByDateRequest.getStartProjectionDate(), getProjectionsByDateRequest.getEndProjectionDate()))
                .thenReturn(List.of());

        assertEquals(ProjectionsNotFoundError.class
                , getProjectionsByDateProcessorCore.process(getProjectionsByDateRequest).getLeft().getClass());
    }
}