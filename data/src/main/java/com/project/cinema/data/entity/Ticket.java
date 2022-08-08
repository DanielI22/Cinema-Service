package com.project.cinema.data.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TICKET")
@Getter
@Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;
    private Long projectionId;
    private Long userId;
    private Double ticketPrice;

    @ManyToOne
    @JoinColumn(name="projectionId",insertable = false,updatable = false)
    private ProjectionEntity projection;
}
