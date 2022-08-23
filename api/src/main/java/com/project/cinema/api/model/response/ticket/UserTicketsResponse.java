package com.project.cinema.api.model.response.ticket;

import com.project.cinema.api.base.OperationResult;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserTicketsResponse implements OperationResult {
    private String userId;
    private List<TicketResponse> tickets;
}
