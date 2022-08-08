package com.project.cinema.data.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "GENRE")
@Getter
@Setter
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreId;
    private String genreName;

    @OneToMany(mappedBy = "genre")
    private Set<ProjectionEntity> projections;
}
