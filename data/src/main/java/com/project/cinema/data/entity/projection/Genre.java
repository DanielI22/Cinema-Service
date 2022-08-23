package com.project.cinema.data.entity.projection;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "GENRE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Long genreId;
    @Column(name = "genre_name")
    private String genreName;

    @OneToMany(mappedBy = "genre")
    private Set<ProjectionEntity> projections;
}
