package com.project.cinema.api.model.response.ticket;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {
    private String ticketId;
    private String projectionId;
//    private String userId;
    private String type;
    private String price;
    private String status;
}