package com.project.cinema.api.model.response.projection;

import com.project.cinema.api.base.OperationResult;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class GetProjectionsSortedByRatingResponse implements OperationResult {
    private List<ProjectionResponse> projectionResponses;
}
