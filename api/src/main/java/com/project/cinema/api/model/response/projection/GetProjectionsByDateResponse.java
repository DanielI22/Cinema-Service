package com.project.cinema.api.model.response.projection;

import com.project.cinema.api.base.OperationResult;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class GetProjectionsByDateResponse implements OperationResult {
    private String startProjectionDate;
    private String endProjectionDate;
    private List<ProjectionResponse> projectionResponses;
}
