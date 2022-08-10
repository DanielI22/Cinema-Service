package com.project.cinema.api.model.response;

import com.project.cinema.api.base.OperationResult;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProjectionsByGenreResponse implements OperationResult {
    private List<ProjectionResponse> projectionResponses;
}
