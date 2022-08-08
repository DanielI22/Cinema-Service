package com.project.cinema.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "PROJECTION")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProjectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectionId;
    private String title;
    private String description;
    private Long genreId;
    private String releaseDate;
    private Double rating;
    private LocalDate projectionDate;
    private LocalTime projectionTime;
    private Double ticketPrice;
    private Integer capacity;

    @ManyToOne
    @JoinColumn(name="genreId",insertable = false,updatable = false)
    private Genre genre;

}
