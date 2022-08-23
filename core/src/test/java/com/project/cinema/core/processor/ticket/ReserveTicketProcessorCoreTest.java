package com.project.cinema.core.processor.ticket;

import com.project.cinema.api.model.request.ticket.ReserveTicketRequest;
import com.project.cinema.api.model.response.ticket.ReserveTicketResponse;
import com.project.cinema.api.model.response.ticket.TicketResponse;
import com.project.cinema.data.entity.projection.ProjectionEntity;
import com.project.cinema.data.entity.user.User;
import com.project.cinema.data.repository.projection.ProjectionRepository;
import com.project.cinema.data.repository.projection.TicketRepository;
import com.project.cinema.data.repository.user.UserRepository;
import com.project.pricing.api.feign.PricingClient;
import com.project.pricing.api.model.PricingResponse;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReserveTicketProcessorCoreTest {
    @Mock
    private ProjectionRepository projectionRepository;
    @Mock
    private ConversionService conversionService;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private PricingClient pricingClient;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ReserveTicketProcessorCore reserveTicketProcessorCore;

    @BeforeEach
    public void setUp() {
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
    public void testTicketResponsePriceEquals10() {
        ReserveTicketRequest reserveTicketRequest = ReserveTicketRequest.builder().projectionId(1L)
                .ticketType("VIP").build();

        final ProjectionEntity projection = ProjectionEntity.builder().projectionId(1L).capacity(10).build();
        when(projectionRepository.findById(reserveTicketRequest.getProjectionId())).thenReturn(Optional.of(projection));
        when(pricingClient.calculateTicketPrice(any())).thenReturn(PricingResponse.builder().ticketPrice(10.).build());
        when(conversionService.convert(any(), any())).thenReturn(TicketResponse.builder().price("10").build());

        ReserveTicketResponse response = reserveTicketProcessorCore.process(reserveTicketRequest).get();
        assertEquals("10", response.getTicket().getPrice());
    }

    @Test
    public void testProjectionCapacityLessThan1() {
        ReserveTicketRequest reserveTicketRequest = ReserveTicketRequest.builder().projectionId(1L)
                .ticketType("VIP").build();

        final ProjectionEntity projection = ProjectionEntity.builder().projectionId(1L).capacity(10).build();

        when(projectionRepository.findById(reserveTicketRequest.getProjectionId())).thenReturn(Optional.of(projection));
        when(pricingClient.calculateTicketPrice(any())).thenReturn(PricingResponse.builder().ticketPrice(10.).build());
        when(conversionService.convert(any(), any())).thenReturn(TicketResponse.builder().price("10").build());

        reserveTicketProcessorCore.process(reserveTicketRequest);
        assertEquals(9, projection.getCapacity());
    }
}