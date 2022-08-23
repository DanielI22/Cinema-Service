package com.project.cinema.core.processor.ticket;

import com.project.cinema.api.model.request.EmptyRequest;
import com.project.cinema.api.model.response.ticket.UserTicketsResponse;
import com.project.cinema.data.entity.projection.Ticket;
import com.project.cinema.data.entity.user.User;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetUserTicketsProcessorCoreTest {
    @Mock
    private ConversionService conversionService;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private GetUserTicketsProcessorCore getUserTicketsProcessorCore;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(new User());
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void TestId1UserTest() {
        final User user = new User();
        user.setId(1L);
        when(userRepository.findByUserName(null)).thenReturn(Optional.of(user));

        Ticket ticket1 = Ticket.builder().userId(1L).build();
        Ticket ticket3 = Ticket.builder().userId(1L).build();

        when(ticketRepository.findAllByUserId(user.getId())).thenReturn(List.of(ticket1, ticket3));

        UserTicketsResponse response = getUserTicketsProcessorCore.process(new EmptyRequest()).get();

        assertEquals(2, response.getTickets().size());
    }
}