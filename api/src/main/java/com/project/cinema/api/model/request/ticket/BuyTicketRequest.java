package com.project.cinema.api.model.request.ticket;

import com.project.cinema.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuyTicketRequest implements OperationInput {
    private Long projectionId;
    private String ticketType;
    private String cardCredentials;
}
