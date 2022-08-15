package com.project.cinema.core.convertor;

import com.project.cinema.api.model.response.ticket.TicketResponse;
import com.project.cinema.data.entity.Ticket;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TicketEntityToTicketResponseConverter implements Converter<Ticket, TicketResponse> {
    @Override
    public TicketResponse convert(Ticket source) {
        return TicketResponse.builder()
                .ticketId(String.valueOf(source.getTicketId()))
                .projectionId(String.valueOf(source.getProjectionId()))
                .type(String.valueOf(source.getType()))
                .price(String.valueOf(source.getTicketPrice()))
                .status(String.valueOf(source.getStatus()))
                .build();
    }
}
