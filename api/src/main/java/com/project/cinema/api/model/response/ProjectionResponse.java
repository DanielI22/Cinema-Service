package com.project.cinema.api.model.response;

import com.project.cinema.api.base.OperationResult;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectionResponse implements OperationResult {
    private String title;
    private String description;
    private String genre;
    private String releaseDate;
    private String rating;
    private String projectionDate;
    private String projectionTime;
    private String ticketPrice;
    private String capacity;
}
