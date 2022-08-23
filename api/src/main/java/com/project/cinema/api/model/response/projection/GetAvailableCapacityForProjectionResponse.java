package com.project.cinema.api.model.response.projection;

import com.project.cinema.api.base.OperationResult;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class GetAvailableCapacityForProjectionResponse implements OperationResult {
    private String projectionId;
    private String projectionTitle;
    private String availableCapacity;
}
