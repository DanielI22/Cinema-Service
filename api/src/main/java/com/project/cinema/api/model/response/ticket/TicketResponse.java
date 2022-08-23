package com.project.cinema.api.model.response.ticket;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TicketResponse {
    private String ticketId;
    private String projectionId;
    private String type;
    private String price;
    private String status;
}