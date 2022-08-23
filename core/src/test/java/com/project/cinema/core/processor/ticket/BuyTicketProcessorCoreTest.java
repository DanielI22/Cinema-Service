package com.project.cinema.core.processor.ticket;

import com.project.cinema.api.error.ticket.InvalidCredentialsError;
import com.project.cinema.api.model.request.ticket.BuyTicketRequest;
import com.project.cinema.api.model.response.ticket.BuyTicketResponse;
import com.project.cinema.api.model.response.ticket.TicketResponse;
import com.project.cinema.data.entity.projection.ProjectionEntity;
import com.project.cinema.data.entity.user.User;
import com.project.cinema.data.repository.projection.ProjectionRepository;
import com.project.cinema.data.repository.projection.TicketRepository;
import com.project.cinema.data.repository.user.UserRepository;
import com.project.payment.api.feign.PaymentClient;
import com.project.payment.api.model.PaymentResponse;
import com.project.pricing.api.feign.PricingClient;
import com.project.pricing.api.model.PricingResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BuyTicketProcessorCoreTest {
    @Mock
    private ProjectionRepository projectionRepository;
    @Mock
    private ConversionService conversionService;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private PricingClient pricingClient;
    @Mock
    private PaymentClient paymentClient;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private BuyTicketProcessorCore buyTicketProcessorCore;


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
    public void testSuccessfullyBoughtTicket() {
        BuyTicketRequest buyTicketRequest = BuyTicketRequest.builder().projectionId(1L)
                .ticketType("VIP").cardCredentials("555552222").build();

        final ProjectionEntity projection = ProjectionEntity.builder().projectionId(1L).capacity(10).build();
        when(projectionRepository.findById(buyTicketRequest.getProjectionId())).thenReturn(Optional.of(projection));
        when(pricingClient.calculateTicketPrice(any())).thenReturn(PricingResponse.builder().ticketPrice(10.).build());
        when(paymentClient.authorizePayment(any())).thenReturn(PaymentResponse.builder().responseStatus(HttpStatus.OK).build());
        when(conversionService.convert(any(), any())).thenReturn(TicketResponse.builder().price("10").status("PAID").build());

        BuyTicketResponse response = buyTicketProcessorCore.process(buyTicketRequest).get();
        assertEquals("PAID", response.getTicket().getStatus());
    }

    @Test
    public void testInvalidCredentials() {
        BuyTicketRequest buyTicketRequest = BuyTicketRequest.builder().projectionId(1L)
                .ticketType("VIP").cardCredentials("INVALID").build();

        final ProjectionEntity projection = ProjectionEntity.builder().projectionId(1L).capacity(10).build();
        when(projectionRepository.findById(buyTicketRequest.getProjectionId())).thenReturn(Optional.of(projection));
        when(pricingClient.calculateTicketPrice(any())).thenReturn(PricingResponse.builder().ticketPrice(10.).build());
        when(paymentClient.authorizePayment(any())).thenReturn(PaymentResponse.builder().responseStatus(HttpStatus.BAD_REQUEST).build());
        when(conversionService.convert(any(), any())).thenReturn(TicketResponse.builder().price("10").status("PAID").build());

        assertEquals(InvalidCredentialsError.class, buyTicketProcessorCore.process(buyTicketRequest).getLeft().getClass());
    }
}