package com.project.cinema.core.processor.projection;

import com.project.cinema.api.model.request.projection.GetAvailableCapacityForProjectionRequest;
import com.project.cinema.api.model.response.projection.GetAvailableCapacityForProjectionResponse;
import com.project.cinema.data.entity.projection.ProjectionEntity;
import com.project.cinema.data.repository.projection.ProjectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GetAvailableCapacityForProjectionProcessorCoreTest {
    @Mock
    private ProjectionRepository projectionRepository;

    @InjectMocks
    private GetAvailableCapacityForProjectionProcessorCore getAvailableCapacityForProjectionProcessorCore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void CapacityTest() {
        GetAvailableCapacityForProjectionRequest getAvailableCapacityForProjectionRequest =
                GetAvailableCapacityForProjectionRequest.builder().projectionId(1L).build();
        ProjectionEntity projection = ProjectionEntity.builder().projectionId(1L).capacity(10).build();

        when(projectionRepository.findById(1L)).thenReturn(Optional.of(projection));
        GetAvailableCapacityForProjectionResponse response =
                getAvailableCapacityForProjectionProcessorCore.process(getAvailableCapacityForProjectionRequest).get();

        assertEquals("10", response.getAvailableCapacity());
    }
}