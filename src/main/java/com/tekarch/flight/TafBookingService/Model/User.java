package com.tekarch.flight.TafBookingService.Model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User{

    private Long id;
    private String username;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters and setters
}