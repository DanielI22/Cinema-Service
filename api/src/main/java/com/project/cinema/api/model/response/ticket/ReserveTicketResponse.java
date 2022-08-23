package com.project.cinema.api.model.response.ticket;

import com.project.cinema.api.base.OperationResult;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ReserveTicketResponse implements OperationResult {
    TicketResponse ticket;
}
