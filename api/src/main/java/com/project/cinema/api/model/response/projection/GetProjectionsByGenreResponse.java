package com.project.cinema.api.model.response.projection;

import com.project.cinema.api.base.OperationResult;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProjectionsByGenreResponse implements OperationResult {
    private String genre;
    private List<ProjectionResponse> projectionResponses;
}
