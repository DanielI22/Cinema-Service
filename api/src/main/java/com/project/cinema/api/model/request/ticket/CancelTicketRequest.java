package com.project.cinema.api.model.request.ticket;

import com.project.cinema.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CancelTicketRequest implements OperationInput {
    private Long ticketId;
}
