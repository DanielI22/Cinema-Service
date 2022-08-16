package com.project.cinema.core.processor.ticket;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.error.ServiceUnavailableError;
import com.project.cinema.api.error.projection.InvalidProjectionIdError;
import com.project.cinema.api.error.ticket.InsufficientBalanceError;
import com.project.cinema.api.error.ticket.InvalidCredentialsError;
import com.project.cinema.api.error.ticket.InvalidTicketTypeError;
import com.project.cinema.api.error.ticket.NoCapacityError;
import com.project.cinema.api.model.request.ticket.BuyTicketRequest;
import com.project.cinema.api.model.response.ticket.BuyTicketResponse;
import com.project.cinema.api.model.response.ticket.TicketResponse;
import com.project.cinema.api.operation.ticket.BuyTicketProcessor;
import com.project.cinema.core.exception.InsufficientBalanceException;
import com.project.cinema.core.exception.InvalidCredentialsException;
import com.project.cinema.core.exception.NoCapacityException;
import com.project.cinema.data.entity.ProjectionEntity;
import com.project.cinema.data.entity.Ticket;
import com.project.cinema.data.repository.ProjectionRepository;
import com.project.cinema.data.repository.TicketRepository;
import com.project.cinema.data.ticketEnum.TicketStatus;
import com.project.cinema.data.ticketEnum.TicketType;
import com.project.payment.api.feign.PaymentClient;
import com.project.payment.api.model.PaymentRequest;
import com.project.payment.api.model.PaymentResponse;
import com.project.pricing.api.feign.PricingClient;
import com.project.pricing.api.model.PricingRequest;
import com.project.pricing.api.model.PricingResponse;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BuyTicketProcessorCore implements BuyTicketProcessor {
    private final TicketRepository ticketRepository;
    private final ProjectionRepository projectionRepository;
    private final ConversionService conversionService;
    private final PricingClient pricingClient;
    private final PaymentClient paymentClient;

    public BuyTicketProcessorCore(TicketRepository ticketRepository, ProjectionRepository projectionRepository, ConversionService conversionService, PricingClient pricingClient, PaymentClient paymentClient) {
        this.ticketRepository = ticketRepository;
        this.projectionRepository = projectionRepository;
        this.conversionService = conversionService;
        this.pricingClient = pricingClient;
        this.paymentClient = paymentClient;
    }

    @Override
    public Either<Error, BuyTicketResponse> process(BuyTicketRequest ticketRequest) {
        return Try.of(() -> {
            final ProjectionEntity projection = projectionRepository.findById(ticketRequest.getProjectionId()).orElseThrow();
            if(projection.getCapacity() == 0) {
                throw new NoCapacityException();
            }

            Ticket ticket = new Ticket();
            ticket.setProjectionId(ticketRequest.getProjectionId());
            ticket.setType(TicketType.valueOf(ticketRequest.getTicketType().toUpperCase()));

            final PricingResponse pricingResponse = pricingClient
                    .calculateTicketPrice(PricingRequest.builder()
                            .capacity(projection.getCapacity())
                            .ticketBasePrice(projection.getTicketPrice())
                            .ticketType(ticketRequest.getTicketType())
                            .build());

            final PaymentResponse paymentResponse = paymentClient
                    .authorizePayment(PaymentRequest.builder()
                            .ticketPrice(pricingResponse.getTicketPrice())
                            .cardCredentials(ticketRequest.getCardCredentials())
                            .build());

            switch (paymentResponse.getResponseStatus()) {
                case BAD_REQUEST -> throw new InvalidCredentialsException();
                case PAYMENT_REQUIRED -> throw new InsufficientBalanceException();
            }


            ticket.setTicketPrice(pricingResponse.getTicketPrice());
            ticket.setStatus(TicketStatus.PAID);
            ticketRepository.save(ticket);
            projection.setCapacity(projection.getCapacity()-1);
            projectionRepository.save(projection);

                return BuyTicketResponse.builder()
                .ticket(conversionService.convert(ticket, TicketResponse.class))
                .build();
            }).toEither()
            .mapLeft(throwable -> {
                if(throwable instanceof NoSuchElementException) {
                    return new InvalidProjectionIdError();
                }
                else if(throwable instanceof NoCapacityException) {
                    return new NoCapacityError();
                }
                else if(throwable instanceof InvalidCredentialsException) {
                    return new InvalidCredentialsError();
                }
                else if(throwable instanceof IllegalArgumentException) {
                    return new InvalidTicketTypeError();
                }
                else if(throwable instanceof InsufficientBalanceException) {
                    return new InsufficientBalanceError();
                }
                return new ServiceUnavailableError();
            });
    }
}
