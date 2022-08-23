package com.project.cinema.core.processor.ticket;

import com.project.cinema.api.error.ticket.TicketNotFoundError;
import com.project.cinema.api.model.request.ticket.CancelTicketRequest;
import com.project.cinema.api.model.response.ticket.CancelTicketResponse;
import com.project.cinema.api.model.response.ticket.TicketResponse;
import com.project.cinema.data.entity.projection.ProjectionEntity;
import com.project.cinema.data.entity.projection.Ticket;
import com.project.cinema.data.entity.user.User;
import com.project.cinema.data.repository.projection.ProjectionRepository;
import com.project.cinema.data.repository.projection.TicketRepository;
import com.project.cinema.data.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CancelTicketProcessorCoreTest {
    @Mock
    private ProjectionRepository projectionRepository;
    @Mock
    private ConversionService conversionService;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CancelTicketProcessorCore cancelTicketProcessorCore;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(new User());
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        when(ticketRepository.save(any())).thenReturn(null);
        when(projectionRepository.save(any())).thenReturn(null);
        final User user = new User();
        user.setId(1L);
        when(userRepository.findByUserName(null)).thenReturn(Optional.of(user));
    }

    @Test
    public void SuccessfullyCancelledTicketStatus() {
        CancelTicketRequest cancelTicketRequest = CancelTicketRequest.builder().ticketId(1L).build();
        Ticket ticket = Ticket.builder().ticketId(1L).userId(1L).projectionId(5L).build();
        ProjectionEntity projection = ProjectionEntity.builder().projectionId(1L).capacity(10).build();

        when(ticketRepository.findById(cancelTicketRequest.getTicketId())).thenReturn(Optional.of(ticket));
        when(projectionRepository.findById(ticket.getProjectionId())).thenReturn(Optional.of(projection));
        when(conversionService.convert(any(), any())).thenReturn(TicketResponse.builder().price("10").status("Cancelled").build());

        CancelTicketResponse response = cancelTicketProcessorCore.process(cancelTicketRequest).get();

        assertEquals("Cancelled", response.getTicket().getStatus());
    }

    @Test
    public void SuccessfullyCancelledTicketCapacityProjection() {
        CancelTicketRequest cancelTicketRequest = CancelTicketRequest.builder().ticketId(1L).build();
        Ticket ticket = Ticket.builder().ticketId(1L).userId(1L).projectionId(5L).build();
        ProjectionEntity projection = ProjectionEntity.builder().projectionId(1L).capacity(10).build();

        when(ticketRepository.findById(cancelTicketRequest.getTicketId())).thenReturn(Optional.of(ticket));
        when(projectionRepository.findById(ticket.getProjectionId())).thenReturn(Optional.of(projection));
        when(conversionService.convert(any(), any())).thenReturn(TicketResponse.builder().price("10").status("Cancelled").build());
        cancelTicketProcessorCore.process(cancelTicketRequest);

        assertEquals(11, projection.getCapacity());
    }

    @Test
    public void NoUserWithGivenTicketId() {
        CancelTicketRequest cancelTicketRequest = CancelTicketRequest.builder().ticketId(1L).build();
        Ticket ticket = Ticket.builder().ticketId(1L).userId(2L).projectionId(5L).build();
        ProjectionEntity projection = ProjectionEntity.builder().projectionId(1L).capacity(10).build();

        when(ticketRepository.findById(cancelTicketRequest.getTicketId())).thenReturn(Optional.of(ticket));
        when(projectionRepository.findById(ticket.getProjectionId())).thenReturn(Optional.of(projection));
        when(conversionService.convert(any(), any())).thenReturn(TicketResponse.builder().price("10").status("Cancelled").build());

        assertEquals(TicketNotFoundError.class, cancelTicketProcessorCore.process(cancelTicketRequest).getLeft().getClass());
    }
}