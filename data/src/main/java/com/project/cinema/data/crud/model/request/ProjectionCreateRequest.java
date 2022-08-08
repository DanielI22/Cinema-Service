package com.project.cinema.data.crud.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ProjectionCreateRequest {
    @NotNull(message = "The movie id is required")
    private Long movieId;
    @NotNull(message = "The date is required")
    @JsonFormat(shape =JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    @NotNull(message = "The time is required")
    @JsonFormat(shape =JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime time;
    @NotNull(message = "The ticket price is required")
    @Min(value = 5, message = "Ticket price must be greater or equal to 5")
    @Max(value = 20, message = "Ticket price must be less than or equal to 20")
    private Double ticketPrice;
    @NotNull(message = "The capacity is required")
    @Min(value = 10, message = "Capacity must be greater or equal to 10")
    @Max(value = 300, message = "Capacity must be less than or equal to 300")
    private Integer capacity;
}
