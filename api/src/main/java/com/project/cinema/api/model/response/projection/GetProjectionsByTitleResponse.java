package com.project.cinema.api.model.response.projection;

import com.project.cinema.api.base.OperationResult;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@Setter
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class GetProjectionsByTitleResponse implements OperationResult {
    private String title;
    private List<ProjectionResponse> projectionResponses;
}
