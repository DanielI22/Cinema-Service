package com.project.cinema.api.model.response.ticket;

import com.project.cinema.api.base.OperationResult;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class CancelTicketResponse implements OperationResult {
    private TicketResponse ticket;
}
