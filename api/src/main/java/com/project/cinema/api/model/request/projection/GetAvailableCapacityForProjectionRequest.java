package com.project.cinema.api.model.request.projection;

import com.project.cinema.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAvailableCapacityForProjectionRequest implements OperationInput {
    private Long projectionId;
}
