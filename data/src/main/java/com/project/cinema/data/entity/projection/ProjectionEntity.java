package com.project.cinema.data.entity.projection;

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
    @Column(name = "projection_id")
    private Long projectionId;
    private String title;
    private String description;
    @Column(name = "genre_id")
    private Long genreId;
    @Column(name = "release_date")
    private String releaseDate;
    private Double rating;
    @Column(name = "projection_date")
    private LocalDate projectionDate;
    @Column(name = "projection_time")
    private LocalTime projectionTime;
    @Column(name = "ticket_price")
    private Double ticketPrice;
    private Integer capacity;

    @ManyToOne
    @JoinColumn(name="genre_id",insertable = false,updatable = false)
    private Genre genre;

}
