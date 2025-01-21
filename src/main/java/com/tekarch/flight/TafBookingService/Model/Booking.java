package com.tekarch.flight.TafBookingService.Model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Booking {
    private Long id;
    private User user;
    private Flight flight;
    private String status; // Booked, Cancelled
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "MyRequest{" +
                "userId='" + user.getId() + '\'' +
                ", flightId='" + flight.getId() + '\'' +
                '}';
    }
//
//   Flight flight= new Flight(private Long id,
//    private String flightNumber,
//    private String departure,
//    private String arrival,
//    private LocalDateTime departureTime,
//    private LocalDateTime arrivalTime,
//    private Double price,
//    private Integer availableSeats)
//    public Booking(Long userId, Long flightId) {
//
//        this.userId = userId;
//        this.flightId = flightId;
//
//    }
    // Getters and Setters
}