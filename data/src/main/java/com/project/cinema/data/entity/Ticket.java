package com.project.cinema.data.entity;


import com.project.cinema.data.ticketEnum.TicketStatus;
import com.project.cinema.data.ticketEnum.TicketType;
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
    @Enumerated(EnumType.STRING)
    private TicketType type;
    private Double ticketPrice;
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @ManyToOne
    @JoinColumn(name="projectionId",insertable = false,updatable = false)
    private ProjectionEntity projection;
}


