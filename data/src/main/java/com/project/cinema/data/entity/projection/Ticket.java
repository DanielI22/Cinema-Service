package com.project.cinema.data.entity.projection;


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
    @Column(name = "ticket_id")
    private Long ticketId;
    @Column(name = "projection_id")
    private Long projectionId;
    @Column(name = "user_id")
    private Long userId;
    @Enumerated(EnumType.STRING)
    private TicketType type;
    @Column(name = "ticket_price")
    private Double ticketPrice;
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @ManyToOne
    @JoinColumn(name="projection_id",insertable = false,updatable = false)
    private ProjectionEntity projection;
}


