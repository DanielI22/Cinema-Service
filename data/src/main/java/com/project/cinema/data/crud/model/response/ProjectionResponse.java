package com.project.cinema.data.crud.model.response;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectionResponse {
    private String title;
    private String description;
    private String genre;
    private String releaseDate;
    private Double rating;
    private LocalDate projectionDate;
    private LocalTime projectionTime;
    private Double ticketPrice;
    private Integer capacity;
}
