package com.tekarch.flight.TafBookingService.Model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Flight {
    private Long id;
    private String flightNumber;
    private String departure;
    private String arrival;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Double price;
    private Integer availableSeats;


}
